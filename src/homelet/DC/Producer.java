package homelet.DC;

/**
 * From F produce P
 *
 * @param <F> the takeIn Type
 * @param <P> the produce Type
 */
public interface Producer<F, P>{
	
	P produce(int index, F e);
}
