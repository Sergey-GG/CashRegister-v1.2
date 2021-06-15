package vntu.fcsa.gonchar;

/**
 * VNTU-FCSA
 * 1-ICT-20(b)
 * Gonchar Sergey
 **/

public interface IProducts {

    default String toProductsTXT() {
        return null;
    }

    default int getId() {
        return 0;
    }

    void setId(int id);

    void setName(String name);

    default String getName() {
        return null;
    }

    void setWeight(double weight);

    default double getWeight() {
        return 0;
    }

    void setCost(double cost);

    default double getCost() {
        return 0;
    }


}
