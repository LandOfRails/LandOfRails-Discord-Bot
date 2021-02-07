package jadennotificater;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jadennotificater.utils.CertificateGetter;

public class JadenNotificater {

	private JadenNotificater() {

	}

	public static final String PANEL = "https://panel.landofrails.net/";
	public static final String WEBSITE = "https://www.landofrails.net/";
	public static final String MAILS = "https://mails.landofrails.net/";
	public static final String PHPMYADMIN = "https://phpmyadmin.landofrails.net/";
	public static final String WEBMIN = "https://webmin.landofrails.net/";
	public static final String LAUNCHER = "https://launcher.landofrails.net/";

	public static final String TEST_EXPIRED = "https://expired.badssl.com/";

	public static void init() {

		testURLs(PANEL, WEBSITE, MAILS, PHPMYADMIN, WEBMIN, LAUNCHER);

	}

	private static void testURLs(String... urls) {

		for (String url : urls) {

			try {
				URL u = new URL(url);
				Optional<LocalDate> opt = CertificateGetter.getDate(u);
				if (opt.isPresent()) {

					LocalDate notAfter = opt.get();

					if (LocalDate.now().isAfter(notAfter)) {
						// Message today
						CustomTimer.init(url, LocalDate.now(), -1);
					} else {
						// Message same day
						CustomTimer.init(url, notAfter, 0);

						// Message day before
						CustomTimer.init(url, notAfter.minusDays(1), 1);

						// Message two days before
						CustomTimer.init(url, notAfter.minusDays(2), 2);

						// Message week before
						CustomTimer.init(url, notAfter.minusDays(7), 7);
					}

				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}

	}

	public static Map<String, LocalDate> getCertificateDates() {
		List<String> arr = Arrays.asList(PANEL, WEBSITE, MAILS, PHPMYADMIN, WEBMIN, LAUNCHER);
		Map<String, LocalDate> data = new HashMap<>();

		for (String link : arr) {
			try {
				URL url = new URL(link);
				Optional<LocalDate> opt = CertificateGetter.getDate(url);
				if (opt.isPresent()) {
					data.put(link, opt.get());
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

}
