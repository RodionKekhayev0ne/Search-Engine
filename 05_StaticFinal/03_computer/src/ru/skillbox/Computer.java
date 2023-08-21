package ru.skillbox;

public class Computer {

    private CPU procesor;
    private RAM aperativeMemory;
    private Memory memory;
    private Display display;
    private Keybord keybord;
    private String vendor;
    private String name;


    public Computer(CPU procesor, RAM aperativeMemory, Memory memory, Display display, Keybord keybord, String vendor, String name) {
        this.procesor = procesor;
        this.aperativeMemory = aperativeMemory;
        this.memory = memory;
        this.display = display;
        this.keybord = keybord;
        this.vendor = vendor;
        this.name = name;
    }

    public CPU getProcesor() {
        return procesor;
    }

    public void setProcesor(CPU procesor) {
        this.procesor = procesor;
    }

    public RAM getAperativeMemory() {
        return aperativeMemory;
    }

    public void setAperativeMemory(RAM aperativeMemory) {
        this.aperativeMemory = aperativeMemory;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public Keybord getKeybord() {
        return keybord;
    }

    public void setKeybord(Keybord keybord) {
        this.keybord = keybord;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalWeight() {
        return procesor.getWeight() + aperativeMemory.getWeight() + memory.getWeight() + display.getWeght() + keybord.getWeight() ;
    }

    @Override
    public String toString() {
        return "----------------------------Computer---------------------------- " + '\n' + '\n' +
                "- Procesor : " + procesor + '\n' +
                "- RAM : " + aperativeMemory + '\n' +
                "- Memory : " + memory + '\n' +
                "- Display : " + display + '\n' +
                "- Keybord : " + keybord + '\n' +
                "- Vendor : " + vendor + '\n' +
                "- Model : " + name;
    }


}
