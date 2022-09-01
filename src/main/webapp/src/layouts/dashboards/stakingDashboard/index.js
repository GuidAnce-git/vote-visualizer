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
import Grid from "@mui/material/Grid";
import MDBox from "../../../components/MDBox";
import DashboardLayout from "../../../examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "../../../examples/Navbars/DashboardNavbar";
import Footer from "../../../examples/Footer";
import StakingEvents from "./components/StakingEvents";

function StakingDashboard() {

    return (

        <DashboardLayout>
            <DashboardNavbar/>
            <MDBox py={3}>
                <Grid item xs={12} md={6} lg={12}>
                    <StakingEvents/>
                </Grid>
            </MDBox>
            <Footer/>
        </DashboardLayout>
    );
}

export default StakingDashboard;
