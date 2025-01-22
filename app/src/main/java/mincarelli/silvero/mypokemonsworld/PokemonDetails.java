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

    public boolean isCaptured() {
        return isCaptured;
    }

    public void setCaptured(boolean captured) {
        this.isCaptured = captured;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public List<TypeSlot> getTypes() {
        return types;
    }

    public void setTypes(List<TypeSlot> types) {
        this.types = types;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public static class Form {
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class TypeSlot {
        @SerializedName("slot")
        private int slot;

        @SerializedName("type")
        private Type type;

        public int getSlot() {
            return slot;
        }

        public void setSlot(int slot) {
            this.slot = slot;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public static class Type {
            @SerializedName("name")
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class Sprites {
        @SerializedName("other")
        private OtherSprites other;

        public OtherSprites getOther() {
            return other;
        }

        public void setOther(OtherSprites other) {
            this.other = other;
        }

        public static class OtherSprites {
            @SerializedName("home")
            private HomeSprites home;

            public HomeSprites getHome() {
                return home;
            }

            public void setHome(HomeSprites home) {
                this.home = home;
            }

            public static class HomeSprites {
                @SerializedName("front_default")
                private String frontDefault;

                public String getFrontDefault() {
                    return frontDefault;
                }

                public void setFrontDefault(String frontDefault) {
                    this.frontDefault = frontDefault;
                }
            }
        }
    }
}