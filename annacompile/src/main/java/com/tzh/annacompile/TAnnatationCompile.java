package com.tzh.annacompile;

import com.google.auto.service.AutoService;
import com.tzh.annation.TRouterPath;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.WillClose;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * create by tuzanhua on 2020/4/18
 */

@AutoService(Processor.class)
public class TAnnatationCompile extends AbstractProcessor {


    private Filer filer;  // 文件写入
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new HashSet<>();
        String canonicalName = TRouterPath.class.getCanonicalName();
        annotations.add(canonicalName);
        return annotations;
    }

    /**
     * 在这个类里面实现相对应的代码
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {


        //结构数据化
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(TRouterPath.class);
        Map<String, String> stringStringMap = new HashMap<>();
        if (elementsAnnotatedWith.size() > 0) {
            for (Element element : elementsAnnotatedWith) {
                TypeElement typeElement = (TypeElement) element;
                TRouterPath annotation = typeElement.getAnnotation(TRouterPath.class);
                String path = annotation.value();
                //获取的是包名 + 类名 com.tzh.router.RouterUtil
                String classAbslout = typeElement.getQualifiedName().toString() + ".class";
                stringStringMap.put(path, classAbslout);
            }

            if (stringStringMap.size() > 0) {
                Writer writer = null;
                String injectUtilName = "RouterRegister"+System.nanoTime();

                try {
                    JavaFileObject sourceFile = filer.createSourceFile("com.tzh.trouter." + injectUtilName);
                    writer = sourceFile.openWriter();
                    writer.write("package com.tzh.trouter;\n" +
                            "\n" +
                            "import com.tzh.router.IRouter;\n" +
                            "import com.tzh.router.TRouter;\n" +
                            "\n" +
                            "public class " + injectUtilName + " implements IRouter {\n" +
                            "\n" +
                            "\n" +

                            "    public void putActivity(){");
                    Set<Map.Entry<String, String>> entries = stringStringMap.entrySet();
                    for (Map.Entry<String, String> entry : entries) {
                        String path = entry.getKey();
                        String classDescribe = entry.getValue();
                        writer.write("TRouter.getInstance().putActivity(\"" + path + "\"," + classDescribe + ");");
                        writer.write("    }\n" +
                                "}");

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
        return false;
    }
}
