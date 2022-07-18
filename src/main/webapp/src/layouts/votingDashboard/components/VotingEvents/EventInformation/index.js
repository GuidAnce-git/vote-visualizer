/**
=========================================================
* Material Dashboard 2 React - v2.1.0
=========================================================

* Product Page: https://www.creative-tim.com/product/material-dashboard-react
* Copyright 2022 Creative Tim (https://www.creative-tim.com)

Coded by www.creative-tim.com

 =========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
*/
import Card from "@mui/material/Card";
import MDBox from "../../../../../components/MDBox";
import MDTypography from "../../../../../components/MDTypography";

function EventInformation() {
  return (
    <Card id="delete-account">
      <MDBox pt={3} px={2}>
        <MDTypography variant="h6" fontWeight="medium">
            Information
        </MDTypography>
      </MDBox>
      <MDBox pt={1} pb={2} px={2}>
          <MDTypography variant="h6" fontWeight="light">
              text
          </MDTypography>
      </MDBox>
    </Card>
  );
}

export default EventInformation;
