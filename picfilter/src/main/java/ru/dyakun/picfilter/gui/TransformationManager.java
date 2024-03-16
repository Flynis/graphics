package ru.dyakun.picfilter.gui;

import ru.dyakun.picfilter.gui.components.dialog.PropertiesDialog;
import ru.dyakun.picfilter.model.ImageManager;
import ru.dyakun.picfilter.model.ImageTransformation;
import ru.dyakun.picfilter.model.proprerty.Property;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class TransformationManager {

    private record TransformStruct(ImageTransformation transform, PropertiesDialog settings) { }

    private final Map<String, TransformStruct> transformations = new HashMap<>();
    private final List<Action> actions = new ArrayList<>();

    public TransformationManager(ImageManager manager, JFrame frame) {
        findTransformations(frame);
        createActions(manager);
    }

    public List<Action> getActions() {
        return actions;
    }

    private void findTransformations(JFrame frame) {
        Collection<Class<?>> classes = ReflectionUtil.findAllClassesInPackage("ru.dyakun.picfilter.transformations");
        System.out.println("Searching image transformations");
        for(var clazz : classes) {
            if(!ImageTransformation.class.isAssignableFrom(clazz)) {
                continue;
            }
            try {
                String name = getSimpleClassName(clazz);
                ImageTransformation transform = (ImageTransformation) clazz.getConstructor().newInstance();
                List<Property> properties = new ArrayList<>();
                for(var field : clazz.getDeclaredFields()) {
                    if(!Property.class.isAssignableFrom(field.getType())) {
                        continue;
                    }
                    properties.add((Property) field.get(transform));
                }
                PropertiesDialog settings = (properties.isEmpty()) ? null : new PropertiesDialog(name, frame, properties);
                transformations.put(name, new TransformStruct(transform, settings));
                System.out.println(name);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                System.out.println("Failed to create instance of " + clazz.getSimpleName());
            }
        }
    }

    private void createActions(ImageManager manager) {
        for(var entry : transformations.entrySet()) {
            Action action = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = (String) getValue(NAME);
                    TransformStruct transform = transformations.get(name);
                    if(transform.settings != null) {
                        transform.settings.show();
                        // TODO pass callback to settings
                    } else {
                        manager.transformImage(transform.transform);
                    }
                }
            };
            String name = entry.getKey();
            String iconPath = getIconPath(name);
            ActionKit.initAction(action, name, iconPath);
            actions.add(action);
        }
    }

    private String getSimpleClassName(Class<?> clazz) {
        String name = clazz.getSimpleName();
        StringBuilder builder = new StringBuilder();
        int i = 0;
        char ch = name.charAt(i);
        while(Character.isUpperCase(ch)) {
            builder.append(ch);
            i++;
            ch = name.charAt(i);
        }
        for(; i < name.length(); i++) {
            ch = name.charAt(i);
            if(Character.isUpperCase(ch)) {
                builder.append(' ');
                builder.append(Character.toLowerCase(ch));
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    private String getIconPath(String name) {
        StringBuilder builder = new StringBuilder();
        builder.append("/icons/");
        int i = 0;
        char ch = name.charAt(i);
        while(Character.isUpperCase(ch)) {
            builder.append(Character.toLowerCase(ch));
            i++;
            ch = name.charAt(i);
        }
        for(; i < name.length(); i++) {
            ch = name.charAt(i);
            if(ch == ' ') {
                builder.append('_');
            } else {
                builder.append(ch);
            }
        }
        builder.append(".png");
        return builder.toString();
    }

}
