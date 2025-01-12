package io.github.some_example_name;

public enum PowerUpType {
    NONE{
        public PowerUp<Bird> create(){
            return null;
        }
    },

    SPEED_BOOST {
        public PowerUp<Bird> create(){
            return new SpeedBoostPowerUp(bird, "SPEED BOOST");
        }
    },

    EXPLOSION{
        public PowerUp<Bird> create(){
            return new ExplosionPowerUp(bird, "EXPLOSION");
        }
    };

    private static Bird bird;

    public static void setBird(Bird bird) {
        PowerUpType.bird = bird;
    }

    public abstract PowerUp<Bird> create();
}
