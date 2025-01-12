package io.github.some_example_name;

import java.io.Serializable;

class TextureState implements Serializable {
    public enum BirdTextureEnum {
        RED("angry-icon.png", 965, 868, 45, 44, 966, 917, 49, 42, 887, 800, 46, 45, "NONE"),
        YELLOW("angry-icon.png", 552, 658, 56, 53, 552, 715, 56, 52, 551, 772, 56, 52, "SPEED_BOOST"),
        BLACK("angry-icon.png", 488, 829, 58, 79, 488, 913, 58, 78, 488, 660, 58, 78, "EXPLOSION");

        private String path;
        private int x, y, width, height;
        private int blinkx, blinky, blinkwidth, blinkheight;
        private int hurtx, hurty, hurtwidth, hurtheight;
        private String powerUp;

        BirdTextureEnum(String s, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, String powerUp) {
            this.path = s;
            this.x = i;
            this.y = i1;
            this.width = i2;
            this.height = i3;
            this.blinkx = i4;
            this.blinky = i5;
            this.blinkwidth = i6;
            this.blinkheight = i7;
            this.hurtx = i8;
            this.hurty = i9;
            this.hurtwidth = i10;
            this.hurtheight = i11;
            this.powerUp = powerUp;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getblinkx() {
            return blinkx;
        }

        public int getblinky() {
            return blinky;
        }

        public int getBlinkwidth() {
            return blinkwidth;
        }

        public int getBlinkheight() {
            return blinkheight;
        }

        public String getPath(){
            return path;
        }

        public int getHurtx(){
            return hurtx;
        }

        public int getHurty(){
            return hurty;
        }

        public int getHurtwidth(){
            return hurtwidth;
        }

        public int getHurtheight(){
            return hurtheight;
        }

        public String getPowerUp(){
            return this.powerUp;
        }
    }

    public enum WoodTextureEnum{
        WOOD("all blocks.png", 883, 305, 204, 22, 884, 237, 248, 18),
        WOOD_LONG("all blocks.png", 884, 261, 248, 18, 885, 237, 248, 18),
        WOOD_STUB("all blocks.png", 798, 213, 83, 39, 798, 297, 83, 40),
        WOOD_SQUARE("all blocks.png", 582, 349, 82, 82, 752, 1, 82, 82),
        WOOD_SMALL("all blocks.png", 798, 451, 82, 19, 1054, 395, 82, 19);

        private String path;
        private int x, y, width, height;
        private int damagedx, damagedy, damagedwidth, damagedheight;
        WoodTextureEnum(String s, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
            this.path = s;
            this.x = i;
            this.y = i1;
            this.width = i2;
            this.height = i3;
            this.damagedx = i4;
            this.damagedy = i5;
            this.damagedwidth = i6;
            this.damagedheight = i7;
        }

        public String getPath(){
            return path;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getDamagedx(){
            return damagedx;
        }

        public int getDamagedy(){
            return damagedy;
        }

        public int getDamagedwidth(){
            return damagedwidth;
        }

        public int getDamagedheight(){
            return damagedheight;
        }
    }

    public enum CatapultTextureEnum{
        LEFTHAND("slingshot.png", 24, 4, 24, 108),
        RIGHTHAND("slingpart.png", 4, 0, 26, 69);

        private String path;
        private final int x;
        private final int y;
        private final int width;
        private final int height;

        CatapultTextureEnum(String image, int i, int i1, int i2, int i3) {
            this.path = image;
            this.x = i;
            this.y = i1;
            this.width = i2;
            this.height = i3;
        }

        public String getPath(){
            return this.path;
        }

        public int getX(){
            return this.x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    public enum PigTextureEnum {
        BIGPIG("angry-icon.png", 786, 573, 78, 77, 616, 574, 77, 76, 616, 814, 78, 73),
        SMALLPIG("angry-icon.png", 552, 878, 46, 45, 966, 488, 46, 44, 700, 910, 46, 43),
        KINGPIG("angry-icon.png", 41, 1, 125, 152, 429, 4, 125, 149, 557, 4, 125, 149),
        MOUSTACHEPIG("angry-icon.png", 678, 159, 108, 97, 173, 400, 109, 97, 170, 804, 108, 96);

        private String path;
        private int x, y, width, height;
        private int blinkx, blinky, blinkwidth, blinkheight;
        private int hurtx, hurty, hurtwidth, hurtheight;
        PigTextureEnum(String s, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
            this.path = s;
            this.x = i;
            this.y = i1;
            this.width = i2;
            this.height = i3;
            this.blinkx = i4;
            this.blinky = i5;
            this.blinkwidth = i6;
            this.blinkheight = i7;
            this.hurtx = i8;
            this.hurty = i9;
            this.hurtwidth = i10;
            this.hurtheight = i11;
        }

        public String getPath(){
            return this.path;
        }

        public int getX(){
            return this.x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getblinkx() {
            return blinkx;
        }

        public int getblinky() {
            return blinky;
        }

        public int getBlinkwidth() {
            return blinkwidth;
        }

        public int getBlinkheight() {
            return blinkheight;
        }

        public int getHurtx(){
            return this.hurtx;
        }

        public int getHurty(){
            return this.hurty;
        }

        public int getHurtwidth(){
            return this.hurtwidth;
        }

        public int getHurtheight(){
            return this.hurtheight;
        }
    }

    public enum GroundTextureEnum {
        GROUND("ground.png", 0, 0, 961, 136);

        private String path;
        private int x, y, width, height;

        GroundTextureEnum(String image, int i, int i1, int i2, int i3) {
            this.path = image;
            this.x = i;
            this.y = i1;
            this.width = i2;
            this.height = i3;
        }

        public String getPath(){
            return this.path;
        }

        public int getX(){
            return this.x;
        }

        public int getY(){
            return this.y;
        }

        public int getWidth(){
            return this.width;
        }

        public int getHeight(){
            return this.height;
        }
    }

    public enum GlassTextureEnum{
        GLASS("all blocks.png", 303, 396, 168, 21, 303, 418, 168, 21),
        GLASS_STUB("all blocks.png", 218, 210, 83, 42, 218, 253, 83, 42),
        GLASS_SQUARE("all blocks.png", 1, 349, 82, 82, 87, 0, 83, 83),
        GLASS_SMALL("all blocks.png", 218, 468, 40, 21, 510, 330, 40, 21);

        private String path;
        private int x, y, width, height;
        private int damagedx, damagedy, damagedwidth, damagedheight;

        GlassTextureEnum(String s, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
            this.path = s;
            this.x = i;
            this.y = i1;
            this.width = i2;
            this.height = i3;
            this.damagedx = i4;
            this.damagedy = i5;
            this.damagedwidth = i6;
            this.damagedheight = i7;
        }

        public String getPath(){
            return this.path;
        }

        public int getX(){
            return x;
        }

        public int getY(){
            return y;
        }

        public int getWidth(){
            return width;
        }

        public int getHeight(){
            return height;
        }

        public int getDamagedx(){
            return damagedx;
        }

        public int getDamagedy(){
            return damagedy;
        }

        public int getDamagedwidth(){
            return damagedwidth;
        }

        public int getDamagedheight(){
            return damagedheight;
        }
    }

    public enum RockTextureEnum {
        ROCK("all blocks.png", 1416, 403, 168, 20, 1416, 426, 169, 20),
        ROCK_STUB("all blocks.png", 1332, 216, 82, 41, 1332, 259, 82, 41),
        ROCK_SQUARE("all blocks.png", 1517, 1, 82, 82, 1601, 1, 82, 82),
        ROCK_LONG("all blocks.png", 1418, 244, 248, 19, 1419, 291, 248, 19);

        private String path;
        private int x, y, width, height;
        private int damagedx, damagedy, damagedwidth, damagedheight;

        RockTextureEnum(String s, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
            this.path = s;
            this.x = i;
            this.y = i1;
            this.width = i2;
            this.height = i3;
            this.damagedx = i4;
            this.damagedy = i5;
            this.damagedwidth = i6;
            this.damagedheight = i7;
        }

        public String getPath() {
            return this.path;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getDamagedx() {
            return damagedx;
        }

        public int getDamagedy() {
            return damagedy;
        }

        public int getDamagedwidth() {
            return damagedwidth;
        }

        public int getDamagedheight() {
            return damagedheight;
        }
    }
}
