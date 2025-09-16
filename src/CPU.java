//credits to Dave Poo on youtube for his video
//https://www.youtube.com/watch?v=qJgsuQoy9bc

public class CPU {
    //Accumulator, Index register X and Y. These are 8 bit registers.
    // The stack pointer is also 8 bits
    byte A, X, Y, SP;
    //the program counter is 16 bits
    short PC;

    //the ram of the system
    byte[] memory = new byte[65536];

    StatusRegister P = new StatusRegister();


    //todo
    void Reset() {

    }
    //update flags
    public void updateZNFlags(byte result) {
        P.set(Flag.ZERO, result == 0);
        P.set(Flag.NEGATIVE, (result & 0x80) != 0);
    }

    //Addressing modes
    public byte Immediate() {
        return memory[PC + 1];
    }

    public byte ZeroPage() {
        return memory[memory[PC + 1] & 0xFF];
    }

    public byte ZeroPageX() {
        int base = memory[PC + 1] & 0xFF;
        int addr = (base + X) & 0xFF; // wrap around zero page
        return memory[addr];
    }
    //only used by LDX and STX
    public byte ZeroPageY() {
        int base = memory[PC + 1] & 0xFF;
        int addr = (base + Y) & 0xFF; // wrap around zero page
        return memory[addr];
    }

    public int Relative() {
        byte offset = memory[PC + 1];
        return PC + 2 + offset;
    }

    public byte Absolute() {
        int low = memory[PC + 1] & 0xFF;
        int high = memory[PC + 2] & 0xFF;
        int addr = (high<< 8) | low;
        return memory[addr];
    }

    public byte AbsoluteX() {
        int low = memory[PC + 1] & 0xFF;
        int high = memory[PC + 2] & 0xFF;
        int addr = (high<< 8) | low;
        return memory[(addr + X) & 0xFFFF];
    }

    public byte AbsoluteY() {
        int low = memory[PC + 1] & 0xFF;
        int high = memory[PC + 2] & 0xFF;
        int addr = (high<< 8) | low;
        return memory[(addr + Y) & 0xFFFF];
    }

    //only JMP can use this one
    //i do NOT get this one
    public int IndirectAddress() {
        int low = memory[PC + 1] & 0xFF;
        int high = memory[PC + 2] & 0xFF;
        int addr = (high<< 8) | low;

        int targetLow = memory[addr] & 0xFF;
        int targetHigh = memory[addr & 0xFF00 | ((addr + 1) & 0x00FF)] & 0xFF;

        return (targetHigh << 8) | targetLow;
    }

    public byte IndirectX() { //Indexed Indirect
        int zpAddr = (memory[PC + 1] + X) & 0xFF;
        int low = memory[zpAddr] & 0xFF;
        int high = memory[(zpAddr + 1) & 0xFF] & 0xFF;
        int addr = (high<< 8) | low;
        return memory[addr];
    }

    public byte IndirectY() {  //Indirect Indexed
        int zpAddr = memory[PC + 1] & 0xFF;
        int low = memory[zpAddr] & 0xFF;
        int high = memory[(zpAddr + 1) & 0xFF] & 0xFF;
        int addr = ((high<< 8) | low) + Y;
        return memory[addr & 0xFFFF];
    }

    //Emulated instructions
    //base method
    public void addWithCarry(byte value) {
        int carryIn = P.isSet(Flag.CARRY) ? 1 : 0;

        int result = (A & 0xFF) + (value & 0xFF) + carryIn;
        //found out that the & 0xFF is to treat A and value as unsigned 8 bit values because java bytes are signed
        P.set(Flag.CARRY, result > 0xFF);
        byte resultByte = (byte) result;

        boolean overflow = (~(A ^ value) & (A ^ resultByte) & 0x80) != 0;
        P.set(Flag.OVERFLOW, overflow);

        A = (byte) result;
        updateZNFlags(A);
    }

    public void ADC_Immediate() {
        byte value = Immediate();
        addWithCarry(value);
        PC += 2;
    }

    public void ADC_ZeroPage() {
        byte value = ZeroPage();
        addWithCarry(value);
        PC += 2;
    }

}

