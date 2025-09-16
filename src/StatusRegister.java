public class StatusRegister {
    private byte value;

    public boolean isSet(Flag flag) {
        return (value & flag.mask) != 0;
    }

    public void set(Flag flag, boolean set) {
        if (set) value |= flag.mask;
        else value &= ~flag.mask;
    }

    public byte toByte() {
        return (byte)(value | Flag.UNUSED.mask); //unused byte should never change
    }

    public void fromByte(byte b) {
        value = (byte)(b | Flag.UNUSED.mask);
    }
}
