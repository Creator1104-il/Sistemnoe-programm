import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Список процессов для запуска
        List<String> processesToStart = new ArrayList<>();
        processesToStart.add("notepad.exe");
        processesToStart.add("start outlookcal:");
        processesToStart.add("mspaint.exe");

        List<Process> runningProcesses = new ArrayList<>();

        // Запуск трех процессов
        System.out.println("Запуск трех процессов...");
        for (String processName : processesToStart) {
            try {
                Process process = Runtime.getRuntime().exec(processName);
                runningProcesses.add(process);
                System.out.println("Запущен процесс: " + processName + " (PID: " + process.pid() + ")");
                Thread.sleep(1000); // Даем процессу время на запуск
            } catch (IOException | InterruptedException e) {
                System.out.println("Не удалось запустить процесс: " + processName);
                e.printStackTrace();
            }
        }

        // Получение информации о процессах
        System.out.println("\nИнформация о запущенных процессах:");
        for (Process process : runningProcesses) {
            printProcessInfo(process);
        }

        // Завершение процессов
        System.out.println("\nЗавершение процессов...");
        for (Process process : runningProcesses) {
            terminateProcess(process);
        }

        // Часть 5: Процесс с вводом имени
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nВведите имя процесса для запуска: ");
        String userProcessName = scanner.nextLine();

        try {
            Process userProcess = Runtime.getRuntime().exec(userProcessName);
            System.out.println("Запущен процесс: " + userProcessName + " (PID: " + userProcess.pid() + ")");

            Thread.sleep(2000); // Даем процессу время на запуск

            printProcessInfo(userProcess);

            System.out.print("Завершить процесс? (д/н): ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("д")) {
                if (terminateProcess(userProcess)) {
                    System.out.println("Процесс завершен");
                } else {
                    System.out.println("Не удалось завершить процесс");
                }
            } else {
                System.out.println("Процесс оставлен запущенным");
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Не удалось запустить процесс: " + userProcessName);
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    // Метод для вывода информации о процессе
    private static void printProcessInfo(Process process) {
        try {
            long pid = process.pid();
            System.out.println("\nИнформация о процессе " + pid + ":");

            // Для Windows используем tasklist, для Linux/MacOS ps
            String os = System.getProperty("os.name").toLowerCase();
            Process infoProcess;

            if (os.contains("win")) {
                infoProcess = Runtime.getRuntime().exec("tasklist /fi \"pid eq " + pid + "\"");
            } else {
                infoProcess = Runtime.getRuntime().exec(new String[]{"ps", "-p", String.valueOf(pid)});
            }

            // Чтение вывода команды
            BufferedReader reader = new BufferedReader(new InputStreamReader(infoProcess.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            infoProcess.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при получении информации о процессе");
            e.printStackTrace();
        }
    }

    // Метод для завершения процесса
    private static boolean forceTerminateProcess(Process process) {
        try {
            long pid = process.pid();
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                // Попробуем сначала обычное завершение
                Process terminateProcess = Runtime.getRuntime().exec("taskkill /pid " + pid);
                int exitCode = terminateProcess.waitFor();

                if (exitCode != 0) {
                    // Если не сработало, принудительное завершение
                    Process forceTerminateProcess = Runtime.getRuntime().exec("taskkill /f /pid " + pid);
                    exitCode = forceTerminateProcess.waitFor();
                }

                return exitCode == 0;
            } else {
                // Для Linux/MacOS
                Process terminateProcess = Runtime.getRuntime().exec("kill -9 " + pid);
                return terminateProcess.waitFor() == 0;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при принудительном завершении процесса");
            e.printStackTrace();
            return false;
        }
    }

    // Измените метод terminateProcess для использования улучшенной версии
    private static boolean terminateProcess(Process process) {
        // Сначала попробуем обычное завершение
        if (forceTerminateProcess(process)) {
            return true;
        }

        // Если не сработало, попробуем найти и завершить по имени
        try {
            long pid = process.pid();
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                // Получим имя процесса и завершим все экземпляры
                Process infoProcess = Runtime.getRuntime().exec("wmic process where processid=" + pid + " get name");
                BufferedReader reader = new BufferedReader(new InputStreamReader(infoProcess.getInputStream()));
                String processName = null;
                String line;

                // Пропускаем заголовок
                reader.readLine();
                if ((line = reader.readLine()) != null) {
                    processName = line.trim();
                }

                if (processName != null && !processName.isEmpty()) {
                    Process killProcess = Runtime.getRuntime().exec("taskkill /f /im " + processName);
                    return killProcess.waitFor() == 0;
                }
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при завершении процесса по имени");
            e.printStackTrace();
        }

        return false;
    }
}