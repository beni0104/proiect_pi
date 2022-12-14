package application;

public class DataSingleton {
		private static final DataSingleton instance = new DataSingleton();
		
		private String userName;
		private String locatie;
		
		private DataSingleton() {};
		public static DataSingleton getInstance() {
			return instance;
		}
		
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getLocatie() {
			return locatie;
		}
		public void setLocatie(String locatie) {
			this.locatie = locatie;
		}
		
		
}
