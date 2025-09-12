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

    //statusregister defined in its own class
    StatusRegister P;
}

