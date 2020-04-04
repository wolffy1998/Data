package cn.mango.annotation;

import net.mindview.util.ProcessFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author mango
 * @Since 2020/3/11 19:12
 **/
public class AtUnit implements ProcessFiles.Strategy {

    static Class<?> testClass;

    static List<String> filedTest = new ArrayList<String>();

    static long testsRun = 0;

    static long failures = 0;

    public static void main(String[] args) {
        ClassLoader.getSystemClassLoader()
                .setDefaultAssertionStatus(true);
        new ProcessFiles(new AtUnit(), "class").start(args);
        if (failures == 0)
            System.out.println("OK(" + testsRun + " tests");
        else {
            System.out.println("(" + testsRun + " tests");
            System.out.println("\n>>> " + failures + " FAILURE" +
                    (failures > 1 ? "S" : "") + " <<<");
            for (String failed : filedTest)
                System.out.println(" " + failed);
        }
    }

    @Override
    public void process(File file) {

    }
}
