package mincarelli.silvero.mypokemonsworld;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Representa la estructura de los detalles de cada Pokémon, que se obtiene al hacer una solicitud
 * para obtener más información sobre un Pokémon específico (usando su ID).
 * Esta clase se usa para almacenar los detalles de un Pokémon, que luego se mostrarán en la UI.
 */
public class PokemonDetails {

    @SerializedName("id")
    private int id;

    @SerializedName("forms")
    private List<Form> forms;

    @SerializedName("types")
    private List<TypeSlot> types;

    @SerializedName("weight")
    private int weight;

    @SerializedName("height")
    private int height;

    @SerializedName("sprites")
    private Sprites sprites;

    private boolean isCaptured;

    /**
     * Devuelve si el Pokémon está capturado.
     *
     * @return true si el Pokémon está capturado; false en caso contrario.
     */
    public boolean isCaptured() {
        return isCaptured;
    }

    /**
     * Establece si el Pokémon está capturado.
     *
     * @param captured true si está capturado; false en caso contrario.
     */
    public void setCaptured(boolean captured) {
        this.isCaptured = captured;
    }

    /**
     * Obtiene el identificador único del Pokémon.
     *
     * @return ID del Pokémon.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del Pokémon.
     *
     * @param id Nuevo ID del Pokémon.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene la lista de formas del Pokémon.
     *
     * @return Lista de formas.
     */
    public List<Form> getForms() {
        return forms;
    }

    /**
     * Establece la lista de formas del Pokémon.
     *
     * @param forms Nueva lista de formas.
     */
    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    /**
     * Obtiene la lista de tipos del Pokémon.
     *
     * @return Lista de tipos.
     */
    public List<TypeSlot> getTypes() {
        return types;
    }

    /**
     * Establece la lista de tipos del Pokémon.
     *
     * @param types Nueva lista de tipos.
     */
    public void setTypes(List<TypeSlot> types) {
        this.types = types;
    }

    /**
     * Obtiene el peso del Pokémon.
     *
     * @return Peso en hectogramos.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Establece el peso del Pokémon.
     *
     * @param weight Nuevo peso en hectogramos.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Obtiene la altura del Pokémon.
     *
     * @return Altura en decímetros.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Establece la altura del Pokémon.
     *
     * @param height Nueva altura en decímetros.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Obtiene los sprites (imágenes) del Pokémon.
     *
     * @return Objeto Sprites con las imágenes.
     */
    public Sprites getSprites() {
        return sprites;
    }

    /**
     * Establece los sprites (imágenes) del Pokémon.
     *
     * @param sprites Nuevos sprites.
     */
    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    /**
     * Clase interna que representa una forma del Pokémon.
     */
    public static class Form {
        @SerializedName("name")
        private String name;

        /**
         * Obtiene el nombre de la forma.
         *
         * @return Nombre de la forma.
         */
        public String getName() {
            return name;
        }

        /**
         * Establece el nombre de la forma.
         *
         * @param name Nuevo nombre de la forma.
         */
        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * Clase interna que representa un tipo del Pokémon y su posición (slot).
     */
    public static class TypeSlot {
        @SerializedName("slot")
        private int slot;

        @SerializedName("type")
        private Type type;

        /**
         * Obtiene la posición del tipo.
         *
         * @return Posición del tipo.
         */
        public int getSlot() {
            return slot;
        }

        /**
         * Establece la posición del tipo.
         *
         * @param slot Nueva posición.
         */
        public void setSlot(int slot) {
            this.slot = slot;
        }

        /**
         * Obtiene los detalles del tipo.
         *
         * @return Objeto Type con los detalles.
         */
        public Type getType() {
            return type;
        }

        /**
         * Establece los detalles del tipo.
         *
         * @param type Nuevo objeto Type.
         */
        public void setType(Type type) {
            this.type = type;
        }

        /**
         * Clase interna que representa un tipo específico.
         */
        public static class Type {
            @SerializedName("name")
            private String name;

            /**
             * Obtiene el nombre del tipo.
             *
             * @return Nombre del tipo.
             */
            public String getName() {
                return name;
            }

            /**
             * Establece el nombre del tipo.
             *
             * @param name Nuevo nombre del tipo.
             */
            public void setName(String name) {
                this.name = name;
            }
        }
    }

    /**
     * Clase interna que representa los sprites (imágenes) del Pokémon.
     */
    public static class Sprites {
        @SerializedName("other")
        private OtherSprites other;

        /**
         * Obtiene los sprites adicionales.
         *
         * @return Objeto OtherSprites.
         */
        public OtherSprites getOther() {
            return other;
        }

        /**
         * Establece los sprites adicionales.
         *
         * @param other Nuevo objeto OtherSprites.
         */
        public void setOther(OtherSprites other) {
            this.other = other;
        }

        /**
         * Clase interna que representa sprites adicionales.
         */
        public static class OtherSprites {
            @SerializedName("home")
            private HomeSprites home;

            /**
             * Obtiene los sprites de la versión "home".
             *
             * @return Objeto HomeSprites.
             */
            public HomeSprites getHome() {
                return home;
            }

            /**
             * Establece los sprites de la versión "home".
             *
             * @param home Nuevo objeto HomeSprites.
             */
            public void setHome(HomeSprites home) {
                this.home = home;
            }

            /**
             * Clase interna que representa los sprites "home".
             */
            public static class HomeSprites {
                @SerializedName("front_default")
                private String frontDefault;

                /**
                 * Obtiene la URL de la imagen principal.
                 *
                 * @return URL de la imagen.
                 */
                public String getFrontDefault() {
                    return frontDefault;
                }

                /**
                 * Establece la URL de la imagen principal.
                 *
                 * @param frontDefault Nueva URL de la imagen.
                 */
                public void setFrontDefault(String frontDefault) {
                    this.frontDefault = frontDefault;
                }
            }
        }
    }
}