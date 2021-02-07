package jadennotificater.utils;

import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class CertificateGetter {

	private CertificateGetter() {

	}

	@SuppressWarnings({ "java:S5527", "java:S4830", "java:S1186", "java:S4423" })
	public static Optional<LocalDate> getDate(URL url) {

		try {

			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };
			// Install the all-trusting trust manager
			final SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());

			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

			conn.setHostnameVerifier(allHostsValid);
			conn.setSSLSocketFactory(sc.getSocketFactory());

			conn.connect();

			Certificate cert = conn.getServerCertificates()[0];
			if (cert instanceof X509Certificate) {
				X509Certificate c = (X509Certificate) cert;
				Date d = c.getNotAfter();
				LocalDate date = convertToLocalDateViaSqlDate(d);
				return Optional.of(date);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return Optional.empty();
	}

	private static LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
		return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
	}

}
