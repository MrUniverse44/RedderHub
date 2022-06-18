package dev.mruniverse.redderhub;

import dev.mruniverse.slimelib.SlimeFiles;
import dev.mruniverse.slimelib.SlimePlatform;

@SuppressWarnings("unused")
public enum RedderFile implements SlimeFiles {
    SETTINGS("settings.yml");

    private final boolean differentFolder;

    private final String file;

    private final String folder;

    private final String resource;

    RedderFile(String file) {
        this.file = file;
        this.resource = file;
        this.differentFolder = false;
        this.folder = "";
    }

    RedderFile(String file,String folder) {
        this.file = file;
        this.resource = file;
        this.differentFolder = true;
        this.folder = folder;
    }

    RedderFile(String file,String folder,String resource) {
        this.file = file;
        this.resource = resource;
        this.differentFolder = true;
        this.folder = folder;
    }

    RedderFile(String file,String folderOrResource,boolean isResource) {
        this.file = file;
        if(isResource) {
            this.resource = folderOrResource;
            this.folder = "";
            this.differentFolder = false;
        } else {
            this.resource = file;
            this.folder = folderOrResource;
            this.differentFolder = true;
        }
    }

    @Override
    public String getFileName() {
        return this.file;
    }

    @Override
    public String getFolderName() {
        return this.folder;
    }

    @Override
    public String getResourceFileName(SlimePlatform platform) {
        if (platform == SlimePlatform.VELOCITY || platform == SlimePlatform.SPONGE) {
            return "/" + this.resource;
        }
        return this.resource;
    }

    @Override
    public boolean isInDifferentFolder() {
        return this.differentFolder;
    }
}
