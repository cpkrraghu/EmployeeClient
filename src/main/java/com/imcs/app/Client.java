package com.imcs.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.imcs.raghu.bonus.pojo.Bonus;
import com.imcs.raghu.bonus.service.BonusService;
import com.imcs.raghu.bonus.service.BonusServiceImpl;
import com.imcs.raghu.bonus.service.EmpBonusService;
import com.imcs.raghu.bonus.service.EmpBonusServiceImpl;
import com.imcs.raghu.bonus.service.EmployeeService;
import com.imcs.raghu.bonus.service.EmployeeServiceImpl;

public class Client {
	final static Logger logger=Logger.getLogger(Client.class);
	BonusService bonusService=new BonusServiceImpl();
	EmpBonusService empBService=new EmpBonusServiceImpl();
	EmployeeService empService=new EmployeeServiceImpl();
	
	final String file="C:\\Data\\EmployeeBonus.txt";
	
	private  void loadData() {
		logger.info("Loading data from file");
		List<Bonus> bonusList=new ArrayList<>();
		File bonusData = new File(file);

		try (BufferedReader fbr = new BufferedReader(new FileReader(bonusData))) {
			if (bonusData.exists()) {

				String line = null;
				boolean keepReading = true;
				int index=0;
				while (keepReading) {
					line = fbr.readLine();
					if (line == null || line.equals("")) {
						break;
					}
					if(index!=0){
						Bonus b = parseLine(line);
						bonusList.add(b);
					}
					index++;
				}
				bonusService.loadBonus(bonusList);
			}
			logger.info("Bonus file loaded successfully");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private Bonus parseLine(String line) {
		String[] tokens = line.split("\\s+");
		Bonus b = new Bonus(Integer.parseInt(tokens[0]),Float.parseFloat(tokens[1]),Float.parseFloat(tokens[1]));
		return b;
	}

	public static void main(String[] args){
		logger.info("Application Started");
		Client c=new Client();
		try(Scanner sc=new Scanner(System.in)){
			//while(true){
				//System.out.println("Choose an option\nA. Load Bonus from File\nB.Allocate Bonus\nC.Exit");
				//String choice=sc.nextLine();
				c.loadData();
				long time=new Date().getTime();
				logger.info("Allocation Started");
				c.empBService.allocateBonus();
				logger.info("Alocation ended"+(new Date().getTime()-time));
				/*switch(choice){
				case "A":
					c.loadData();
					break;
				case "B":
					c.empBService.allocateBonus();
					logger.info("Application Ended");
					break;
				case "C":
					logger.info("Application Ended");
					System.exit(0);
					break;
				default:
					System.out.println("Enter a valid choice");
					break;
				}*/
			//}
		}
	}

}
