package me.zurex.ouroboros.classfile;

/**
 * Created by zurex on 2017/8/15.
 * Make life more fun
 */
public class ClassFile {
    private long magic;
    private char minorVersion;
    private char majorVersion;
    private ConstantPool constantPool;
    private char accessFlag;
    private char thisClass;
    private char superClass;
    private char[] interfaces;
    private MemberInfo[] fields;
    private MemberInfo[] methods;
    private AttributeInfo[] attributes;

    public static ClassFile parse(byte[] data){
        ClassReader reader = new ClassReader(data);
        ClassFile cf = new ClassFile();
        cf.init(reader);
        return cf;
    }

    public String getClassName(){
        return constantPool.getClassName(thisClass);
    }

    public String getSuperClassName(){
        return constantPool.getClassName(superClass);
    }

    public String[] getInterfaceNames(){
        String[] interfaceNames = new String[interfaces.length];
        for (int i=0;i<interfaceNames.length;i++){
            interfaceNames[i] = constantPool.getClassName(interfaces[i]);
        }
        return interfaceNames;
    }

    private void init(ClassReader reader){
        readAndCheckMagic(reader);
        readAndCheckVersion(reader);
        constantPool = readConstanPool(reader);

        accessFlag = reader.readUint16();
        thisClass = reader.readUint16();
        superClass = reader.readUint16();

        fields = readMembers(reader);
        methods = readMembers(reader);
        attributes = readAttributes(reader);
    }

    /**
     * check it is java file
     * magic: 0xCAFEBABE
     * @param reader
     */
    private void readAndCheckMagic(ClassReader reader){
        magic = reader.readUint32();
        if (0 != (magic & 0xCAFEBABE)){
            throw new ClassFormatError("not java magic");
        }
    }

    /**
     * read and make sure jvm support class version
     * @param reader
     */
    private void readAndCheckVersion(ClassReader reader){
        minorVersion = reader.readUint16();
        majorVersion = reader.readUint16();

        switch (majorVersion){
            case 45:
                return;
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
                if (0 == minorVersion){
                    return;
                }
        }
        throw new UnsupportedClassVersionError(String.format(
                "current jvm dont support version:%s.%s",
                majorVersion,
                minorVersion
        ));
    }

    private ConstantPool readConstanPool(ClassReader reader){
        return null;
    }

    private MemberInfo[] readMembers(ClassReader reader){
        return null;
    }

    private AttributeInfo[] readAttributes(ClassReader reader){
        return null;
    }
}