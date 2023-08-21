package ru.skillbox;

public class Computer {
    private String computerName = "";
    private String cpuName = "";
    private String gpuName = "";
    private int ramQuantity;

    public Computer(String computerName) {
        this.computerName = computerName;
    }

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public String getGpuName() {
        return gpuName;
    }

    public void setGpuName(String gpuName) {
        this.gpuName = gpuName;
    }

    public int getRamQuantity() {
        return ramQuantity;
    }

    public void setRamQuantity(int ramQuantity) {
        this.ramQuantity = ramQuantity;
    }

}
