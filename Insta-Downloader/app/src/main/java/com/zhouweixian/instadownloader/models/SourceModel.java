package com.zhouweixian.instadownloader.models;

import java.util.List;

public class SourceModel {
    /**
     * is_video : false
     * images : [{"display_resources":[{"config_height":523,"config_width":640,"src":"https://scontent-lax3-2.cdninstagram.com/vp/59357f13ce2b32c01ba4c611f78561f7/5D56F9D6/t51.2885-15/sh0.08/e35/s640x640/54247291_2037924866500698_250947563542652970_n.jpg?_nc_ht=scontent-lax3-2.cdninstagram.com"},{"config_height":613,"config_width":750,"src":"https://scontent-lax3-2.cdninstagram.com/vp/09698675e1edb8176c827fa501e2aa06/5D6567D6/t51.2885-15/sh0.08/e35/s750x750/54247291_2037924866500698_250947563542652970_n.jpg?_nc_ht=scontent-lax3-2.cdninstagram.com"},{"config_height":884,"config_width":1080,"src":"https://scontent-lax3-2.cdninstagram.com/vp/a36b09d5a9a058c3eb30e898ecc287bb/5D71ED6C/t51.2885-15/fr/e15/s1080x1080/54247291_2037924866500698_250947563542652970_n.jpg?_nc_ht=scontent-lax3-2.cdninstagram.com"}],"is_video":false,"video_url":"","thumbnail_src":"","video_duration":0,"shortcode":"","__typename":""}]
     * video : {"video_url":"","video_duration":0,"thumbnail_src":"","shortcode":"","__typename":""}
     * shortcode : BvXBNeojp9L
     * __typename :
     */

    private boolean is_video;
    private VideoBean video;
    private String shortcode;
    private String __typename;
    private List<ImagesBean> images;

    public boolean isIs_video() {
        return is_video;
    }

    public void setIs_video(boolean is_video) {
        this.is_video = is_video;
    }

    public VideoBean getVideo() {
        return video;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public String get__typename() {
        return __typename;
    }

    public void set__typename(String __typename) {
        this.__typename = __typename;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class VideoBean {
        /**
         * video_url :
         * video_duration : 0
         * thumbnail_src :
         * shortcode :
         * __typename :
         */

        private String video_url;
        private float video_duration;
        private String thumbnail_src;
        private String shortcode;
        private String __typename;

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public float getVideo_duration() {
            return video_duration;
        }

        public void setVideo_duration(float video_duration) {
            this.video_duration = video_duration;
        }

        public String getThumbnail_src() {
            return thumbnail_src;
        }

        public void setThumbnail_src(String thumbnail_src) {
            this.thumbnail_src = thumbnail_src;
        }

        public String getShortcode() {
            return shortcode;
        }

        public void setShortcode(String shortcode) {
            this.shortcode = shortcode;
        }

        public String get__typename() {
            return __typename;
        }

        public void set__typename(String __typename) {
            this.__typename = __typename;
        }
    }

    public static class ImagesBean {
        /**
         * display_resources : [{"config_height":523,"config_width":640,"src":"https://scontent-lax3-2.cdninstagram.com/vp/59357f13ce2b32c01ba4c611f78561f7/5D56F9D6/t51.2885-15/sh0.08/e35/s640x640/54247291_2037924866500698_250947563542652970_n.jpg?_nc_ht=scontent-lax3-2.cdninstagram.com"},{"config_height":613,"config_width":750,"src":"https://scontent-lax3-2.cdninstagram.com/vp/09698675e1edb8176c827fa501e2aa06/5D6567D6/t51.2885-15/sh0.08/e35/s750x750/54247291_2037924866500698_250947563542652970_n.jpg?_nc_ht=scontent-lax3-2.cdninstagram.com"},{"config_height":884,"config_width":1080,"src":"https://scontent-lax3-2.cdninstagram.com/vp/a36b09d5a9a058c3eb30e898ecc287bb/5D71ED6C/t51.2885-15/fr/e15/s1080x1080/54247291_2037924866500698_250947563542652970_n.jpg?_nc_ht=scontent-lax3-2.cdninstagram.com"}]
         * is_video : false
         * video_url :
         * thumbnail_src :
         * video_duration : 0
         * shortcode :
         * __typename :
         */

        private boolean is_video;
        private String video_url;
        private String thumbnail_src;
        private float video_duration;
        private String shortcode;
        private String __typename;
        private List<DisplayResourcesBean> display_resources;

        public boolean isIs_video() {
            return is_video;
        }

        public void setIs_video(boolean is_video) {
            this.is_video = is_video;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getThumbnail_src() {
            return thumbnail_src;
        }

        public void setThumbnail_src(String thumbnail_src) {
            this.thumbnail_src = thumbnail_src;
        }

        public float getVideo_duration() {
            return video_duration;
        }

        public void setVideo_duration(float video_duration) {
            this.video_duration = video_duration;
        }

        public String getShortcode() {
            return shortcode;
        }

        public void setShortcode(String shortcode) {
            this.shortcode = shortcode;
        }

        public String get__typename() {
            return __typename;
        }

        public void set__typename(String __typename) {
            this.__typename = __typename;
        }

        public List<DisplayResourcesBean> getDisplay_resources() {
            return display_resources;
        }

        public void setDisplay_resources(List<DisplayResourcesBean> display_resources) {
            this.display_resources = display_resources;
        }

        public static class DisplayResourcesBean {
            /**
             * config_height : 523
             * config_width : 640
             * src : https://scontent-lax3-2.cdninstagram.com/vp/59357f13ce2b32c01ba4c611f78561f7/5D56F9D6/t51.2885-15/sh0.08/e35/s640x640/54247291_2037924866500698_250947563542652970_n.jpg?_nc_ht=scontent-lax3-2.cdninstagram.com
             */

            private int config_height;
            private int config_width;
            private String src;

            public int getConfig_height() {
                return config_height;
            }

            public void setConfig_height(int config_height) {
                this.config_height = config_height;
            }

            public int getConfig_width() {
                return config_width;
            }

            public void setConfig_width(int config_width) {
                this.config_width = config_width;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }
        }
    }
}
