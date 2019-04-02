
package NeljanSuora.domain;


public class User {
    
    private String name;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public String getNameModified() {
            String editedName = this.name.trim().toUpperCase();
            return editedName;
        }
        

        @Override
        public String toString() {
            return this.name;
        }
}
