package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Day04 extends Day<Long> {

    public static void main(String[] args) {
        Day04 d = new Day04();
        d.init("/day04.txt");
        d.printResult();
    }

    List<Password> data;

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            data = new ArrayList<>();
            List<String> lines = ReadTxtFile.readFileAsStringList(args[0]);

            Password currentPassword = new Password();
            for(String line : lines) {
                if(line.isEmpty()) {
                    data.add(currentPassword);
                    currentPassword = new Password();
                    continue;
                }
                String[] elems = line.split(" ");
                for(String elem : elems) {
                    String[] d = elem.split(":");
                    switch(d[0]) {
                        case "byr" :
                            currentPassword.setBirthYear(d[1].trim());
                            break;
                        case "iyr" :
                            currentPassword.setIssueYear(d[1].trim());
                            break;
                        case "eyr" :
                            currentPassword.setExpirationYear(d[1].trim());
                            break;
                        case "hgt" :
                            currentPassword.setHeight(d[1].trim());
                            break;
                        case "hcl" :
                            currentPassword.setHairColor(d[1].trim());
                            break;
                        case "ecl" :
                            currentPassword.setEyeColor(d[1].trim());
                            break;
                        case "pid" :
                            currentPassword.setPassportID(d[1].trim());
                            break;
                        case "cid" :
                            currentPassword.setCountryID(d[1].trim());
                            break;
                    }
                }
            }
            data.add(currentPassword);

        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Long part1() {
        long result = 0;

        for(Password p : data) {
            result += p.isValid() ? 1 : 0;
        }

        return result;
    }

    public Long part2() {
        long result = 0;

        for(Password p : data) {
            result += p.isValid2() ? 1 : 0;
        }
        return result;
    }

    @Data
    public class Password {
        public String birthYear;
        public String issueYear;
        public String expirationYear;
        public String height;
        public String hairColor;
        public String eyeColor;
        public String passportID;
        public String countryID;

        public boolean isValid() {
            if(birthYear == null || birthYear.isEmpty()) {
                return false;
            }
            if(issueYear == null || issueYear.isEmpty()) {
                return false;
            }
            if(expirationYear == null || expirationYear.isEmpty()) {
                return false;
            }
            if(height == null || height.isEmpty()) {
                return false;
            }
            if(hairColor == null || hairColor.isEmpty()) {
                return false;
            }
            if(eyeColor == null || eyeColor.isEmpty()) {
                return false;
            }
            return passportID != null && !passportID.isEmpty();
        }

        public boolean isValid2() {
            // byr (Birth Year) - four digits; at least 1920 and at most 2002.
            if(birthYear == null || birthYear.length() != 4) {
                return false;
            }
            try {
                int year = Integer.parseInt(birthYear);
                if(year < 1920 || year > 2002) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }

            // iyr (Issue Year) - four digits; at least 2010 and at most 2020.
            if(issueYear == null || issueYear.length() != 4) {
                return false;
            }
            try {
                int year = Integer.parseInt(issueYear);
                if(year < 2010 || year > 2020) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }

            // eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
            if(expirationYear == null || expirationYear.length() != 4) {
                return false;
            }
            try {
                int year = Integer.parseInt(expirationYear);
                if(year < 2020 || year > 2030) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }

            // hgt (Height) - a number followed by either cm or in:
            //    If cm, the number must be at least 150 and at most 193.
            //    If in, the number must be at least 59 and at most 76.
            if(height == null || height.length() <= 2) {
                return false;
            }
            String type = height.substring(height.length()-2);
            try {
                switch (type) {
                    case "cm" :
                        int sizeCm = Integer.parseInt(height.substring(0,height.length()-2));
                        if(sizeCm < 150 || sizeCm > 193) {
                            return false;
                        }
                        break;
                    case "in" :
                        int sizeIn = Integer.parseInt(height.substring(0,height.length()-2));
                        if(sizeIn < 59 || sizeIn > 76) {
                            return false;
                        }
                        break;
                    default:
                        return false;
                }
            } catch (Exception e) {
                return false;
            }

            // hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
            if(hairColor == null || hairColor.length() != 7) {
                return false;
            }
            if(!hairColor.matches("^#[a-f0-9]{6}$")) {
                return false;
            }

            // ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
            if(eyeColor == null || eyeColor.length() != 3) {
                return false;
            }
            switch (eyeColor) {
                case "amb":
                case "blu":
                case "brn":
                case "gry":
                case "grn":
                case "hzl":
                case "oth":
                    break;
                default:
                    return false;
            }

            // pid (Passport ID) - a nine-digit number, including leading zeroes.
            if(passportID == null || passportID.length() != 9) {
                return false;
            }
            return passportID.matches("^[0-9]{9}$");
        }

    }

}
