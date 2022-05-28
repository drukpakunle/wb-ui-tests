package ru.wildberries.enums.system;

public enum Bom {

    UTF8(new byte[]{(byte) 0xef, (byte) 0xbb, (byte) 0xbf}),
    UTF16BE(new byte[]{(byte) 0xfe, (byte) 0xff}),
    UTF16LE(new byte[]{(byte) 0xff, (byte) 0xfe}),
    UTF32BE(new byte[]{0x00, 0x00, (byte) 0xfe, (byte) 0xff}),
    UTF32LE(new byte[]{(byte) 0xff, (byte) 0xfe, 0x00, 0x00});

    private final String header;

    Bom(byte[] bom) {
        header = new String(bom);
    }

    public String getHeader() {
        return header;
    }
}
