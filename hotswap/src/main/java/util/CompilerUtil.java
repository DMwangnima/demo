package util;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CompilerUtil {

    public static final void compiler(String className) {

        String path = "script";
        String filePath = path + File.separator + className + ".java";
        Path pathObj = Paths.get(filePath);
        if (!Files.exists(pathObj)) {
            System.out.println("{" + filePath + "}文件不存在");
            return;
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
        StandardJavaFileManager sjf = compiler.getStandardFileManager(diagnosticCollector, null, null);
        Iterable<? extends JavaFileObject> it = sjf.getJavaFileObjects(filePath);
        JavaCompiler.CompilationTask task = compiler.getTask(null, sjf, diagnosticCollector, null, null, it);

        try {
            if (!task.call()) {
                System.out.println("{" + className + "}编译失败");
            } else {
                System.out.println("编译成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                sjf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
