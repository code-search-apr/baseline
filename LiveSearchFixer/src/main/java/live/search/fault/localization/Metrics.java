package live.search.fault.localization;

/**
 * Metrics of computing suspicious value of suspicious lines.
 */
public class Metrics {
	
	public Metric generateMetric(String metricStr) {
		Metric metric = null;
		if (metricStr.equals("Ample")) {
			metric = new Ample();
		} else if (metricStr.equals("Anderberg")) {
			metric = new Anderberg();
		} else if (metricStr.equals("Dice")) {
			metric = new Dice();
		} else if (metricStr.equals("Fagge")) {
			metric = new Fagge();
		} else if (metricStr.equals("Gp13")) {
			metric = new Gp13();
		} else if (metricStr.equals("Hamming")) {
			metric = new Hamming();
		} else if (metricStr.equals("Jaccard")) {
			metric = new Jaccard();
		} else if (metricStr.equals("Kulczynski1")) {
			metric = new Kulczynski1();
		} else if (metricStr.equals("M1")) {
			metric = new M1();
		} else if (metricStr.equals("Naish1")) {
			metric = new Naish1();
		} else if (metricStr.equals("Naish2")) {
			metric = new Naish2();
		} else if (metricStr.equals("Ochiai")) {
			metric = new Ochiai();
		} else if (metricStr.equals("Qe")) {
			metric = new Qe();
		} else if (metricStr.equals("RogersTanimoto")) {
			metric = new RogersTanimoto();
		} else if (metricStr.equals("SimpleMatching")) {
			metric = new SimpleMatching();
		} else if (metricStr.equals("Sokal")) {
			metric = new Sokal();
		} else if (metricStr.equals("SorensenDice")) {
			metric = new SorensenDice();
		} else if (metricStr.equals("Tarantula")) {
			metric = new Tarantula();
		} else if (metricStr.equals("Wong1")) {
			metric = new Wong1();
		} if (metricStr.equals("Zoltar")) {
			metric = new Zoltar();
		}
		return metric;
	}

	public interface Metric {
		double value(int ef, int ep, int nf, int np);
	}
	
	private class Ample implements Metric {
		public double value(int ef, int ep, int nf, int np) {
			return Math.abs((ef / ((double) (ef + nf))) - (ep / ((double) (ep + np))));
		}
	}
	
	private class Anderberg implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        return ef / ((double) (ef + 2 * (ep + nf)));
	    }
	}
	
	private class Dice implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        return  2*ef / ((double) (ef + (ep + nf)));
	    }
	}

	private class Fagge implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	    	int _false = ef + nf;
	    	if (_false == 0) {
	    		return 0d;
	    	} else {
	    		return ef / _false;
	    	}
	    }
	}
	
	private class Gp13 implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        return ef * (1 + 1 / (double) (2*ep + ef));
	    }
	}
	
	private class Hamming implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        return  ef + np;
	    }
	}
	
	private class Jaccard implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        return ef / ((double) (ef + ep + nf));
	    }
	}
	
	private class Kulczynski1 implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        return ef / ((double) (nf + ep));
	    }
	}
	
	private class M1 implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        return (ef + np) / ((double) (nf + ep));
	    }
	}
	
	private class Naish1 implements Metric {
		public double value(int ef, int ep, int nf, int np) {
			if (ef == 0)
				return np;
			return -1;
		}
	}
	
	private class Naish2 implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        return ef - (ep / ((double) (ep + np + 1)));
	    }
	}
	
	private class Ochiai implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        return ef/Math.sqrt((ef+ep)*(ef+nf))*10;
	    }
	}
	
	private class Qe implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        return  ef / ((double) (ef + ep));
	    }
	}
	
	private class RogersTanimoto implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        return (ef + np) / (ef + np + 2 * (nf + ep));
	    }
	}
	
	private class SimpleMatching implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        return (ef + np) / (ef + nf + ep + np);
	    }
	}
	
	private class Sokal implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        return 2 * (ef + np) / ( 2 * (ef + np) + nf + ep);
	    }
	}
	
	private class SorensenDice implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        return  2*ef / ((double) (2*ef + (ep + nf)));
	    }
	}
	
	private class Tarantula implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        if(ef+nf == 0) {
	            return 0;
	        }
	        return (ef / ((double) (ef + nf))) / ((ef / ((double)(ef - nf))) + (ep/ ((double) ep + np))) ;
	    }
	}
	
	private class Wong1 implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        return  ef;
	    }
	}
	
	private class Zoltar implements Metric {
	    public double value(int ef, int ep, int nf, int np) {
	        return ef / (ef + nf + ep + (10000 * nf * ep)/(double)ef);
	    }
	}
}
