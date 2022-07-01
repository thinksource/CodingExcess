package mypack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Supplier;

public class VersionNumber  implements Comparable<VersionNumber>{
	Integer xx;
	Integer yy;
	Integer zz;

	public Integer getXx() {
		return xx;
	}

	public void setXx(Integer xx) {
		this.xx = xx;
	}

	public Integer getYy() {
		return yy;
	}

	public void setYy(Integer yy) {
		this.yy = yy;
	}

	public Integer getZz() {
		return zz;
	}

	public void setZz(Integer zz) {
		this.zz = zz;
	}

	public static void main(String[] args) {
		String[] exp1 = {"12-34-86","01-34-85","34-34-19","02-34-34","01-34-24","23-34-18","24-34-38"};
//		String[] exp1 = {"2147483648-00-98"};
		List<String> input = new ArrayList<String>();
		for(int i=0; i<exp1.length;i++) {
			input.add(exp1[i]);
		}
		Predicate<VersionNumber> btf = n -> {return n.compareTo(new VersionNumber(8,0,50))>0;};
		List<VersionNumber> output=sortAndConvert(input, Optional.of(btf));
		System.out.println(output);
	}
	
	public VersionNumber(int major, int minor, int patch) {
		xx=major;
		yy=minor;
		zz=patch;
	}
	

	
	public static List<VersionNumber> sortAndConvert(List<String> versions, Optional<Predicate<VersionNumber>> filter){

		return versions.stream().map(vs->{
			if(vs.matches("^[0-9]+-[0-9]+-[0-9]+$")) {
				String[] tmp = vs.split("-");
				try {
					int major = Integer.valueOf(tmp[0]);
					int minor = Integer.valueOf(tmp[1]);
					int patch = Integer.valueOf(tmp[2]);
					VersionNumber t=new VersionNumber(major, minor, patch);
					if(filter==null || filter.isEmpty())
						return t;
					else {
						if(filter.get().test(t)) return t;
						else return null;
					}
					// the major exception is integer larger than Integer MAX
				}catch(NumberFormatException e) {
					return null;
				}
			}
			return null;
		}).filter(Objects::nonNull).sorted(new Comparator<VersionNumber>() {
			@Override
			public int compare(VersionNumber o1, VersionNumber o2) {
				return o2.compareTo(o1);
			}
		})
		.collect(Collectors.toList());
	}

	@Override
	public int compareTo(VersionNumber o) {
		if(xx!=o.getXx()) {
			return xx.compareTo(o.getXx());
		}else if(yy!=o.getYy()) {
			return yy.compareTo(o.getYy());
		}
		return zz.compareTo(o.getZz());
	}

	@Override
	public String toString() {
		return  String.format("%02d", xx) + "-" + String.format("%02d", yy) + "-" + String.format("%02d", zz);
	}
	
	

}
