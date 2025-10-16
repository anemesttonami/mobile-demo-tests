package helpers;

import java.io.IOException;
import java.net.Socket;

public class AppiumServerManager {
    private static Process appiumProcess;
    private static Process emulatorProcess;

    public static void startEmulator(String device) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("G:\\AndroidSDK\\emulator\\emulator.exe", "-avd", device, "-no-snapshot-load");
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        emulatorProcess = pb.start();

        // Ждем запуска эмулятора
        waitForDevice(60000);
    }

    public static void startServer() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "appium", "--base-path", "/wd/hub");
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        appiumProcess = pb.start();

        waitForPort(4723, 30000);
    }

    public static void stopServer() {
        if (appiumProcess != null) {
            appiumProcess.destroy();
        }
        if (emulatorProcess != null) {
            emulatorProcess.destroy();
        }
    }

    private static void waitForPort(int port, int timeoutMs) throws InterruptedException {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < timeoutMs) {
            try (Socket socket = new Socket("localhost", port)) {
                return;
            } catch (IOException e) {
                Thread.sleep(1000);
            }
        }
        throw new RuntimeException("Appium server failed to start within " + timeoutMs + "ms");
    }

    private static void waitForDevice(int timeoutMs) throws InterruptedException, IOException {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < timeoutMs) {
            ProcessBuilder pb = new ProcessBuilder("adb", "devices");
            Process process = pb.start();
            String output = new String(process.getInputStream().readAllBytes());
            process.waitFor();

            if (output.contains("device") && !output.contains("offline")) {
                return; // Устройство найдено и готово
            }
            Thread.sleep(5000);
        }
        throw new RuntimeException("Emulator failed to start within " + timeoutMs + "ms");
    }

    public static void ensureUiautomator2Installed() throws IOException, InterruptedException {
        ProcessBuilder listPb = new ProcessBuilder("cmd", "/c", "appium", "driver", "list", "--installed");
        Process listProcess = listPb.start();

        String output = new String(listProcess.getInputStream().readAllBytes());
        listProcess.waitFor();

        if (!output.toLowerCase().contains("uiautomator2")) {
            ProcessBuilder installPb = new ProcessBuilder("cmd", "/c", "appium", "driver", "install", "uiautomator2");
            installPb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            installPb.redirectError(ProcessBuilder.Redirect.INHERIT);
            Process installProcess = installPb.start();
            installProcess.waitFor();
        }
    }
}