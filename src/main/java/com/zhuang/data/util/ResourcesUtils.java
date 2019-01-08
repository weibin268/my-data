package com.zhuang.data.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * Created by zhuang on 4/19/2018.
 */
public class ResourcesUtils {

	public static Collection<String> getResources(Pattern pattern) {
		ArrayList<String> result = new ArrayList<String>();
		String classPathProperty = System.getProperty("java.class.path", ".");
		String pathSeparator = System.getProperty("path.separator");
		String[] classPathList = classPathProperty.split(pathSeparator);
		for (String classPath : classPathList) {
			result.addAll(getResources(classPath, pattern));
		}
		return result;
	}

	private static Collection<String> getResources(String classPath, Pattern pattern) {
		ArrayList<String> result = new ArrayList<String>();
		File file = new File(classPath);
		if (file.isDirectory()) {
			result.addAll(getResourcesFromDirectory(file, pattern));
		} else {
			result.addAll(getResourcesFromJarFile(file, pattern));
		}
		return result;
	}

	private static Collection<String> getResourcesFromJarFile(File file, Pattern pattern) {
		ArrayList<String> result = new ArrayList<String>();
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(file);
		} catch (ZipException e) {
			throw new Error(e);
		} catch (IOException e) {
			throw new Error(e);
		}
		Enumeration<?> zipEntries = zipFile.entries();
		while (zipEntries.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) zipEntries.nextElement();
			String fileName = zipEntry.getName();
			boolean accept = pattern.matcher(fileName).matches();
			if (accept) {
				result.add(fileName);
			}
		}
		try {
			zipFile.close();
		} catch (IOException e1) {
			throw new Error(e1);
		}
		return result;
	}

	private static Collection<String> getResourcesFromDirectory(File directory, Pattern pattern) {
		ArrayList<String> result = new ArrayList<String>();
		File[] fileList = directory.listFiles();
		for (File file : fileList) {
			if (file.isDirectory()) {
				result.addAll(getResourcesFromDirectory(file, pattern));
			} else {
				try {
					String fileName = file.getCanonicalPath();
					boolean accept = pattern.matcher(fileName).matches();
					if (accept) {
						result.add(fileName);
					}
				} catch (IOException e) {
					throw new Error(e);
				}
			}
		}
		return result;
	}
}
