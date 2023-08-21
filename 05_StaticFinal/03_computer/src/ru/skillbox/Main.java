package ru.skillbox;

public class Main {

    public static void main(String[] args) {

        CPU cpu = new CPU(2,4,"intel",0.455);
        RAM ram = new RAM(RAMtype.DDR4,16,500);
        Memory memory = new Memory(MemoryType.SDD,1000,1500);
        Display display = new Display(13.4,DisplayType.IPS,700);
        Keybord keybord = new Keybord(KeyCapType.MECHANIC, KeyLights.YES,600);

        Computer computer = new Computer(cpu,ram,memory,display,keybord,"Acer","Predator");
        System.out.println(computer);

    }
}
