package coffeemachine;

public class Resources {
    int waterMl, milkMl, coffeeBeans, money, cups;


    public Resources(int waterMl, int milkMl, int coffeeBeans,  int cups, int money) {
        this.waterMl = waterMl;
        this.milkMl = milkMl;
        this.coffeeBeans = coffeeBeans;
        this.money = money;
        this.cups = cups;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCups() {
        return cups;
    }

    public void setCups(int cups) {
        this.cups = cups;
    }

    public int getWaterMl() {
        return waterMl;
    }

    public void setWaterMl(int waterMl) {
        this.waterMl = waterMl;
    }

    public int getMilkMl() {
        return milkMl;
    }

    public void setMilkMl(int milkMl) {
        this.milkMl = milkMl;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public void setCoffeeBeans(int coffeeBeans) {
        this.coffeeBeans = coffeeBeans;
    }

    @Override
    public String toString() {
        return "Resources{" +
                "waterMl=" + waterMl +
                ", milkMl=" + milkMl +
                ", coffeeBeans=" + coffeeBeans +
                ", money=" + money +
                ", cups=" + cups +
                '}';
    }
}

