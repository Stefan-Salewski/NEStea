public class StatusRegister {
    private byte value = 0x34; // Default value: unused bit (bit 5) is always set

    // Flag bit positions
    private static final int CARRY = 0;
    private static final int ZERO = 1;
    private static final int INTERRUPT_DISABLE = 2;
    private static final int DECIMAL_MODE = 3;
    private static final int BREAK = 4;
    private static final int UNUSED = 5; // Always set to 1
    private static final int OVERFLOW = 6;
    private static final int NEGATIVE = 7;

    // Getters
    public boolean getCarry()             { return getFlag(CARRY); }
    public boolean getZero()              { return getFlag(ZERO); }
    public boolean getInterruptDisable()  { return getFlag(INTERRUPT_DISABLE); }
    public boolean getDecimalMode()       { return getFlag(DECIMAL_MODE); }
    public boolean getBreak()             { return getFlag(BREAK); }
    public boolean getOverflow()          { return getFlag(OVERFLOW); }
    public boolean getNegative()          { return getFlag(NEGATIVE); }

    // Setters
    public void setCarry(boolean set)             { setFlag(CARRY, set); }
    public void setZero(boolean set)              { setFlag(ZERO, set); }
    public void setInterruptDisable(boolean set)  { setFlag(INTERRUPT_DISABLE, set); }
    public void setDecimalMode(boolean set)       { setFlag(DECIMAL_MODE, set); }
    public void setBreak(boolean set)             { setFlag(BREAK, set); }
    public void setOverflow(boolean set)          { setFlag(OVERFLOW, set); }
    public void setNegative(boolean set)          { setFlag(NEGATIVE, set); }

    // Internal helpers
    private boolean getFlag(int bit) {
        return (value & (1 << bit)) != 0;
    }

    private void setFlag(int bit, boolean set) {
        if (set) value |= (1 << bit);
        else value &= ~(1 << bit);
    }

    // Push to stack (returns byte)
    public byte toByte() {
        return (byte)(value | (1 << UNUSED)); // Ensure unused bit is always set
    }

    // Pull from stack (sets value)
    public void fromByte(byte b) {
        value = (byte)(b | (1 << UNUSED)); // Ensure unused bit stays set
    }
}
