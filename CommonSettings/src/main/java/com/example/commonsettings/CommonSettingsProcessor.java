package com.example.commonsettings;

import com.example.commonsettings.configmanager.ConfigManager;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("com.example.commonsettings.CommonSettings")
public class CommonSettingsProcessor extends AbstractProcessor {

    public static final String DEFAULT_VALUE_LOADER = "default_loader";
    public static final String DEFAULT_ACCESSOR = "default_accessor";

    private static class SettingItemInfo {
        String name;
        CommonSettings.Type type;
        String defaultValue;
        String accessor;
    }

    private List<SettingItemInfo> items = new ArrayList<>();

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        printMessage("begin to process CommonSettingsProcessor");
        boolean res = false;

        if (processCommonSettings(roundEnvironment)) {
            res = generateFile();
        }

        printMessage("end of process CommonSettingsProcessor");
        return res;
    }

    private boolean processCommonSettings(RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(CommonSettings.class);
        if (elements == null || elements.size() < 1) {
            printMessage("processCommonSettings : elements is null or empty");
            return false;
        }

        for (Element element : elements) {
            if (element.getKind() != ElementKind.FIELD) {
                printMessage("processCommonSettings : the element kind is not ok, it is " + element.getKind());
                continue;
            }

            Set<Modifier> modifiers = element.getModifiers();
            if (!(modifiers.contains(Modifier.STATIC) && modifiers.contains(Modifier.FINAL))) {
                printMessage("processCommonSettings : the element modifier is not ok");
                continue;
            }

            VariableElement variableElement = (VariableElement) element;
            TypeMirror variable = variableElement.asType();
            TypeMirror string = processingEnv.getElementUtils().getTypeElement("java.lang.String").asType();
            Types typeUtils = processingEnv.getTypeUtils();

            if (!typeUtils.isSameType(variable, string)) {
                printMessage("processCommonSettings : the element's type is not ok , it is " + variable);
                continue;
            }

            SettingItemInfo settingItemInfo = new SettingItemInfo();

            String name = variableElement.getConstantValue().toString();
            CommonSettings annotation = variableElement.getAnnotation(CommonSettings.class);
            CommonSettings.Type type = annotation.type();
            String accessor = annotation.accessor();
            String defaultValue = annotation.defaultValue();

            settingItemInfo.name = name;
            settingItemInfo.type = type;
            if (accessor != null && !accessor.isEmpty()) {
                settingItemInfo.accessor = accessor;
            } else {
                settingItemInfo.accessor = DEFAULT_ACCESSOR;
            }

            if (defaultValue != null && !defaultValue.isEmpty()) {
                settingItemInfo.defaultValue = defaultValue;
            } else {
                settingItemInfo.defaultValue = DEFAULT_VALUE_LOADER;
            }

            items.add(settingItemInfo);
        }

        return true;
    }

    private boolean generateFile() {
        ClassName ConfigDef = ClassName.get("com.example.commonsettings.configmanager"
                , "ConfigDef");

        TypeSpec.Builder commonSettingsDefBuilder = TypeSpec.classBuilder("CommonSettingsDef")
                .superclass(ConfigDef);

        ClassName steward = ClassName.get("com.example.commonsettings.configmanager", "ConfigSteward");
        ClassName configFlyweightFactory = ClassName.get("com.example.commonsettings.configmanager", "ConfigFlyweightFactory");
        ClassName aa = ClassName.get(ConfigManager.Type.class);

        MethodSpec.Builder registerKeyMethod = MethodSpec.methodBuilder("registerKey")
                .addAnnotation(Override.class).addModifiers(Modifier.PUBLIC).returns(TypeName.VOID)
                .addParameter(ParameterSpec.builder(steward, "steward").build())
                .addParameter(ParameterSpec.builder(configFlyweightFactory, "configFlyweightFactory").build());

        registerKeyMethod.addStatement("super.registerKey(steward, configFlyweightFactory)");

        for (SettingItemInfo settingItemInfo : items) {
            ConfigManager.Type type = TypeMap(settingItemInfo.type);
            registerKeyMethod.addStatement("register($S, $T.$L, $S, $S)"
                    , settingItemInfo.name, ClassName.get("com.example.commonsettings.configmanager.ConfigManager", "Type"), type.name(), settingItemInfo.accessor, settingItemInfo.defaultValue);
        }

        commonSettingsDefBuilder.addMethod(registerKeyMethod.build());

        JavaFile javaFile = JavaFile.builder("com.example.commonsettings.configmanager"
                , commonSettingsDefBuilder.build()).build();

        Filer filer = processingEnv.getFiler();

        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private ConfigManager.Type TypeMap(CommonSettings.Type type) {
        switch (type) {
            case BOOLEAN:
                return ConfigManager.Type.BOOLEAN;
            case INT:
                return ConfigManager.Type.INT;
            case STRING:
                return ConfigManager.Type.STRING;
            case FLOAT:
            case DOUBLE:
                return ConfigManager.Type.FLOAT;
        }

        return null;
    }

    private void printMessage(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, msg);
    }
}
