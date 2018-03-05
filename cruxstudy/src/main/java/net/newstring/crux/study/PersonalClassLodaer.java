package net.newstring.crux.study;

/**
 * PersonalClassLodaer
 *
 * @author lic
 * @date 2018/2/8
 */
public class PersonalClassLodaer extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }



}
