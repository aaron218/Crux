package net.newstring.crux.type.constant;

/**
 * SUPPORTED_DB
 * 支持的类型 并包含版本号，查找类型枚举时，首先匹配version，然后判断name，version如果通配(*) ，则直接返回
 *
 * @author lic
 * @date 2018/5/18
 */
public enum SUPPORTED_DB {
    /**
     *
     */
    PGSQL("postgresql", "*"),

    UNDEFINED("undefined", "*");

    private String name;
    private String version;


    SUPPORTED_DB(String name, String version) {
        this.name = name;
        this.version = version;
    }

    SUPPORTED_DB getValue(String name, String version) {
        if (name == null || version == null) {
            return UNDEFINED;
        }
        for (SUPPORTED_DB supportedDb : SUPPORTED_DB.values()) {
            boolean matched = name.equalsIgnoreCase((supportedDb.name.toLowerCase()))
                    && ("*".equals(version) || supportedDb.version.toLowerCase().equalsIgnoreCase(version));
            if (matched) {
                return supportedDb;
            }
        }
        return UNDEFINED;
    }

}
