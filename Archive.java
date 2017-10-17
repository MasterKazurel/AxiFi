

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;

public class Archive {
	private newLinkedList<Record> weightStack;
	String[] monthArray;
	SimpleDateFormat allFormat = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
	SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
	SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
	SimpleDateFormat yearMonth = new SimpleDateFormat("yyyyMM");
	NumberFormat findFormat = new DecimalFormat("00");
	JComboBox monthList;
	ArrayList<Record> foundRecords = new ArrayList<Record>();
	
	//makes Archive object from linkedlist
	public Archive (newLinkedList<Record> weightStack){
		this.weightStack = weightStack;
		setMonths();
		//makeDropdown();
	}
	
	//goes through list to see what months records exist for. Populates list of months. 
	public void setMonths(){
	 ArrayList<String> tempList = new ArrayList<String>();

		for (int i = 1; i<weightStack.size(); i++){
			Record temp = weightStack.get(i);
			Timestamp t = temp.timestamp;
			String month = monthFormat.format(t);
			
			if (month.equals(new String("01")) && !tempList.contains("January")){
				tempList.add("January");
			}
			else if (month.equals(new String("02")) && !tempList.contains("February")){
				tempList.add("February");
			}
			else if (month.equals(new String("03")) && !tempList.contains("March")){
				tempList.add("March");
			}
			else if (month.equals(new String("04")) && !tempList.contains("April")){
				tempList.add("April");
			}
			else if (month.equals(new String("05")) && !tempList.contains("May")){
				tempList.add("May");
			}
			else if (month.equals(new String("06")) && !tempList.contains("June")){
				tempList.add("June");
			}
			else if (month.equals(new String("07")) && !tempList.contains("July")){
				tempList.add("July");
			}
			else if (month.equals(new String("08")) && !tempList.contains("August")){
				tempList.add("August");
			}
			else if (month.equals(new String("09")) && !tempList.contains("September")){
				tempList.add("September");
			}
			else if (month.equals(new String("10")) && !tempList.contains("October")){
				tempList.add("October");
			}
			else if (month.equals(new String("11")) && !tempList.contains("November")){
				tempList.add("November");
			}
			else if (month.equals(new String("12")) && !tempList.contains("December")){
				tempList.add("December");
			}
		}
		monthArray = new String[tempList.size()];
		int k = tempList.size()-1;
		for (int n = 0; n < tempList.size() ; n++){
			monthArray[n] = tempList.get(k);
			k--;
		}
	}
	
	//uses list of months to make the combo box
	public void makeDropdown(){
		monthList = new JComboBox(monthArray);
		//monthList.setSelectedIndex(0);
	}
	
	public JComboBox getDropdown(){
		return monthList;
	}
	
	//searches through list for all records that match that selected month! 
	public ArrayList<Record> findRecords(newLinkedList<Record> r) {
		String temp = monthList.getSelectedItem().toString();
		String toFind = "2017"+compareMonths(temp);
		int toFindInd = monthList.getSelectedIndex()+1;
		String date = "";
		
		int index =1;
		foundRecords = new ArrayList<Record>();
		while (index < r.size()){
			date = yearMonth.format(r.get(index).timestamp);
			if(date.compareTo(toFind) == 0)
			{
				foundRecords.add(r.get(index));
			}
			index++;
		}
		return foundRecords;
	}
	
	//checks case to see what string is needed for that month on dropdown box
	public String compareMonths(String name){
		String num = "";
		if (name.equals(new String("January"))){
			num="01";
		}
		else if (name.equals(new String("February"))){
			num="02";
		}
		else if (name.equals(new String("March"))){
			num="03";
		}
		else if (name.equals(new String("April"))){
			num="04";
		}
		else if (name.equals(new String("May"))){
			num="05";
		}
		else if (name.equals(new String("June"))){
			num="06";
		}
		else if (name.equals(new String("July"))){
			num="07";
		}
		else if (name.equals(new String("August"))){
			num="08";
		}
		else if (name.equals(new String("September"))){
			num="09";
		}
		else if (name.equals(new String("October"))){
			num="10";
		}
		else if (name.equals(new String("November"))){
			num="11";
		}
		else if (name.equals(new String("December"))){
			num="12";
		}
		return num;
	}

		

	
}
