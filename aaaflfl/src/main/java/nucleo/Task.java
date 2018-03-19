/**
 * @author Vanilson Pires
 * 18 de mar de 2018 2018-03-18
 *
 */
package nucleo;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Vanilson Pires
 * 18 de mar de 2018 2018-03-18
 *
 */
public abstract class Task implements Runnable{
	
	private Executor executor = Executors.newSingleThreadExecutor();

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public abstract void run();
	
	public void start(){
		executor.execute(this);
	}

}
