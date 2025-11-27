# Employee Management System - Login Guide

## Quick Start

When you run the application, you'll see demo credentials automatically displayed. The system comes pre-loaded with sample data for testing.

---

## Default Login Credentials

### Admin Account (Full Access)
- **Username:** `admin`
- **Password:** `admin123`
- **Access Level:** Full system access to all features

### Staff Accounts (Limited Access)

#### 1. John Doe - Software Developer (IT Department)
- **Username:** `john.doe`
- **Password:** `john123`
- **Employee ID:** emp001
- **Department:** IT Department
- **Position:** Software Developer
- **Salary:** $75,000

#### 2. Jane Smith - HR Manager (HR Department)
- **Username:** `jane.smith`
- **Password:** `jane123`
- **Employee ID:** emp002
- **Department:** HR Department
- **Position:** HR Manager
- **Salary:** $65,000

#### 3. Mike Johnson - Accountant (Finance Department)
- **Username:** `mike.johnson`
- **Password:** `mike123`
- **Employee ID:** emp003
- **Department:** Finance Department
- **Position:** Accountant
- **Salary:** $60,000

---

## Access Levels

### Admin Can:
- Manage all employees (Add, View, Update, Delete)
- Manage departments
- Process payroll (salary records, bonuses, deductions)
- View and approve attendance
- Approve/reject leave requests
- Generate and export reports
- Create user accounts for employees
- Search and filter employees by various criteria
- Sort employees by different attributes

### Staff Can:
- View their own profile
- Mark attendance (Check-in/Check-out)
- Request leave
- View their own attendance records
- View their own leave requests
- View their own salary slip

---

## Testing Workflows

### Workflow 1: Admin Operations
1. Login as admin (`admin` / `admin123`)
2. View all employees (3 pre-loaded)
3. Add a new employee
4. Create a user account for the new employee (Option 8: User Management)
5. Process payroll for employees
6. Approve leave requests
7. Generate reports

### Workflow 2: Employee Self-Service
1. Login as John Doe (`john.doe` / `john123`)
2. View your profile details
3. Mark attendance - Check-in
4. Request leave (e.g., Sick Leave for 2 days)
5. View your attendance records
6. View your salary slip

### Workflow 3: Complete Flow
1. **As Admin:**
   - Login as admin
   - Create a new employee (emp004)
   - Go to User Management (Option 8)
   - Create user account for emp004 with STAFF role
   - Logout

2. **As New Employee:**
   - Login with the new credentials
   - Check-in attendance
   - Request leave
   - Logout

3. **As Admin:**
   - Login as admin again
   - View pending leave requests
   - Approve the leave request
   - Generate employee report

---

## Sample Data Included

The system comes pre-loaded with:
- **1 Admin user** (admin)
- **3 Staff users** (john.doe, jane.smith, mike.johnson)
- **3 Employees** (emp001, emp002, emp003)
- **3 Departments** (IT, HR, Finance)
- **1 Sample salary record** (for John Doe)

---

## How to Create New Employee Login

1. **Login as Admin**
2. **Add Employee:** Menu → 1: Employee Management → 1: Add Employee
3. **Create User Account:** Menu → 8: User Management → 1: Create User Account
   - Enter username (e.g., employee.name)
   - Enter password
   - Select role: STAFF
   - Link to Employee ID (the ID you just created)
4. **Employee Can Now Login** with the username and password

---

## Troubleshooting

**Can't login?**
- Verify you're using the correct username and password (case-sensitive)
- Check if the account exists in User Management (admin only)

**Staff can't see full menu?**
- This is expected - staff accounts have limited access
- Only admin accounts can see all management options

**Forgot demo credentials?**
- From the main menu, select Option 3: View Demo Credentials
- Or refer to this guide

---

## Notes

- All staff accounts are linked to employee records
- You must create an employee first before creating a staff user account
- Admin account (`admin`) is not linked to any employee record
- The system uses in-memory storage, so data resets when you restart the application
