package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sun.javafx.collections.UnmodifiableObservableMap;

public class Equalizer {
	private HashMap<Category, ArrayList<Product>> equalizer;
	
	public Equalizer() {
		this.equalizer = new HashMap<>();
	}
	
	public Map<Category, ArrayList<Product>> getEqualizer() {
		//�� ��� ������ DAO ������ ������ �� ���������� 
		//���������� ����� ������ ���������� �������� � ��� JSP �� � ��������� 
		//� � ������� ���;
		return this.equalizer;
	}

}
