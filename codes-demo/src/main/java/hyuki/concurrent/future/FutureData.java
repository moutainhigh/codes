package hyuki.concurrent.future;

import java.util.concurrent.ExecutionException;

/**
 * “提货单”，“提货的票据”
 * @author SXW
 * 
 * 关键就是这个“提货单”的
 * 1、回填真实数据
 * 2、获取真实数据怎么写
 * 这两个接口，就是线程同步的逻辑了
 */
public class FutureData implements Data {
	
	// XXX:技巧
	private boolean ready = false;// true:真正的“蛋糕”已经烤好了

	// 真正的业务逻辑数据
	private RealData realdata = null;
	
	private ExecutionException exception = null;
	
	/**
	 * 使用对象锁，监视器模式保护成员变量
	 */
	@Override
	public synchronized String getContent() throws ExecutionException {// *重要
		// 如果没有真实数据没有准备好，阻塞
		while (!ready) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (exception != null) {
			throw exception;
		}

		return realdata.getContent();
	}
	
	/**
	 * 设置真实数据的对外方法
	 * @param realdata
	 */
	public synchronized void setRealData(RealData realdata){// *重要
		
		// XXX:技巧
		// 保证只被设置一次
		if (ready) {
			return;
		}
		
		this.realdata=realdata;
		this.ready=true;
		
		// XXX:自己又忘了notify了！！
		notifyAll();
	}

	/**
	 * 包装异常
	 * @param throwable
	 */
	public synchronized void setException(Throwable throwable) {// *重要
		if (ready) {
			return;
		}
		// 设置异常
		this.exception=new ExecutionException(throwable);
		this.ready=true;
		notifyAll();
	}
	
}
