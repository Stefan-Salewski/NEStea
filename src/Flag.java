public enum Flag {
    CARRY(0x01),
    ZERO(0x02),
    INTERRUPT_DISABLE(0x04),
    DECIMAL_MODE(0x08),
    BREAK(0x10),
    UNUSED(0x20),
    OVERFLOW(0x40),
    NEGATIVE(0x80);
    
    public final int mask;
    Flag(int mask) {this.mask = mask;}
}
