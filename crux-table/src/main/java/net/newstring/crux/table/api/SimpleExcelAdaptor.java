package net.newstring.crux.table.api;

import net.newstring.crux.table.common.ExcelSource;

import java.util.List;
import java.util.stream.Stream;

/**
 * SimpleExcelAdaptor
 * 简单转换器，可以按照需要将指定的Excel文件转换到 Json CSV 流 或者指定对象的List
 * <p>是工具内置的基本转换组件，主要用于一般性的应用，对于不同场景下的特殊要求，应该使用对应的优化版本，优化版本会需要更多的配置
 *
 * @author lic
 * @date 2018/4/9
 */
public interface SimpleExcelAdaptor {

    <T> List<T> getObjectList(ExcelSource source, Class<T> tClass);

    <T> Stream<T> getObjectStream(ExcelSource source, Class<T> tClass);


}
