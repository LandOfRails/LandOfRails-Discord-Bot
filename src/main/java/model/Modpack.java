package model;

public class Modpack {
    public Modpack(String name, String title, String shortcut, String modpackVersion, String minecraftVersion, int organisation, String key, String locationOnServer, String imageUrl, String downloadedImage) {
        Name = name;
        Title = title;
        Shortcut = shortcut;
        MinecraftVersion = minecraftVersion;
        ModpackVersion = modpackVersion;
        Organisation = organisation;
        Key = key;
        LocationOnServer = locationOnServer;
        ImageUrl = imageUrl;
        DownloadedImage = downloadedImage;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getShortcut() {
        return Shortcut;
    }

    public void setShortcut(String shortcut) {
        Shortcut = shortcut;
    }

    public String getMinecraftVersion() {
        return MinecraftVersion;
    }

    public void setMinecraftVersion(String minecraftVersion) {
        MinecraftVersion = minecraftVersion;
    }

    public String getModpackVersion() {
        return ModpackVersion;
    }

    public void setModpackVersion(String modpackVersion) {
        ModpackVersion = modpackVersion;
    }

    public int getOrganisation() {
        return Organisation;
    }

    public void setOrganisation(int organisation) {
        Organisation = organisation;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getLocationOnServer() {
        return LocationOnServer;
    }

    public void setLocationOnServer(String locationOnServer) {
        LocationOnServer = locationOnServer;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getDownloadedImage() {
        return DownloadedImage;
    }

    public void setDownloadedImage(String downloadedImage) {
        DownloadedImage = downloadedImage;
    }

    public String Name;
    public String Title;
    public String Shortcut;
    public String MinecraftVersion;
    public String ModpackVersion;
    public int Organisation;
    public String Key;
    public String LocationOnServer;
    public String ImageUrl;
    public String DownloadedImage;

    @Override
    public String toString() {
        return "Modpack{" +
                "Name='" + Name + '\'' +
                ", Title='" + Title + '\'' +
                ", Shortcut='" + Shortcut + '\'' +
                ", MinecraftVersion='" + MinecraftVersion + '\'' +
                ", ModpackVersion='" + ModpackVersion + '\'' +
                ", Organisation=" + Organisation +
                ", Key='" + Key + '\'' +
                ", LocationOnServer='" + LocationOnServer + '\'' +
                ", ImageUrl='" + ImageUrl + '\'' +
                ", DownloadedImage='" + DownloadedImage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Modpack)) {
            return false;
        } else {
            Modpack modpack = (Modpack) obj;
            if (!Shortcut.equalsIgnoreCase(modpack.getShortcut()) && !Title.equalsIgnoreCase(modpack.getTitle()) && !Name.equalsIgnoreCase(modpack.getName()) && Organisation != modpack.Organisation) {
                return false;
            }
            return true;
        }
    }
}
