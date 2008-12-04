package eu.kostia.util;

import java.util.*;

/**
 * Console Progress bar
 * 
 * The output looks like this:
 * 
 * <pre>
 * 100% [================================] in 5,00s
 * </pre>
 * 
 * The bar increments on the same cursor line.
 * 
 * If you run the progress bar in ant, remember to set the system property
 * <sysproperty key="run.with.ant" value="false" />
 * 
 * @author c.cerbo
 * 
 */
public class ConsoleProgressBar {
	final private static boolean RUN_WITH_ANT = new Boolean(System
			.getProperty("run.with.ant"));

	private long total;

	private long startTime;

	private String label;

	private int currentPct = 0;

	private int margin = 0;

	/**
	 * Create a new instance and start to measure the time
	 * 
	 * @param total
	 */
	public ConsoleProgressBar(long total) {
		this.total = total;
		this.startTime = System.currentTimeMillis();
	}

	public ConsoleProgressBar(String label, long total) {
		this(total);
		this.label = label;
	}

	public void setMargin(int margin) {
		if ((margin % 5) != 0) {
			throw new IllegalArgumentException(
					"Margig: Only multiple of 5 allowed!");
		}

		if (margin > 50) {
			throw new IllegalArgumentException(
					"Margin cannot be greater than 50!");
		}

		this.margin = margin;
	}

	/**
	 * Update the progress bar with the current progress
	 * 
	 * @param current
	 *            The current absolute value. The percentage value will be
	 *            calculated.
	 */
	public void setCurrent(long current) {
		int tempPct = (int) (100 * current / total);

		if (tempPct == currentPct) {
			return;
		}

		// take care of margin only if runned with ant
		if (RUN_WITH_ANT && (tempPct - currentPct) < margin) {
			return;
		}

		currentPct = tempPct;

		char backspace = RUN_WITH_ANT ? '\n' : '\r';

		if (label != null) {
			System.out.printf(backspace + "%s %3d%% %s", label, currentPct,
					bar());
		} else {
			System.out.printf(backspace + "%3d%% %s", currentPct, bar());
		}

		if (current >= total) {
			long elapsed = System.currentTimeMillis() - startTime;
			System.out.printf(" in %.2fs\n", (double) (elapsed / 1000));
		}
	}

	private String bar() {
		int k = 3;
		int scale = currentPct / k;
		int length = 100 / k + 1;

		char[] line = new char[length];
		Arrays.fill(line, ' ');
		line[0] = '[';
		line[length - 1] = ']';
		if (scale > 0) {
			Arrays.fill(line, 1, scale, '=');
		}

		return String.valueOf(line);
	}

	public static void main(String[] args) throws Exception {
		int n = 130;
		ConsoleProgressBar progressBar = new ConsoleProgressBar("test", n);
		progressBar.setMargin(10);
		for (int i = 0; i <= n; i++) {
			progressBar.setCurrent(i);
			Thread.sleep(50);
		}
	}

}
