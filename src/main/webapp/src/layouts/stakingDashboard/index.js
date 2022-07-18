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
import WeekendIcon from '@mui/icons-material/Weekend';
import MDBox from "../../components/MDBox";
import DashboardLayout from "../../examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "../../examples/Navbars/DashboardNavbar";
import Footer from "../../examples/Footer";
import ReportsBarChart from "../../examples/Charts/BarCharts/ReportsBarChart";
import ReportsLineChart from "../../examples/Charts/LineCharts/ReportsLineChart";
import ComplexStatisticsCard from "../../examples/Cards/StatisticsCards/ComplexStatisticsCard";
import reportsBarChartData from "./data/reportsBarChartData";
import reportsLineChartData from "./data/reportsLineChartData";
import LeaderboardIcon from '@mui/icons-material/Leaderboard';
import StakingEvents from "./components/StakingEvents";

function StakingDashboard() {
    const {sales, tasks} = reportsLineChartData;

    return (
        <DashboardLayout>
            <DashboardNavbar/>
            <MDBox py={3}>
                <Grid item xs={12} md={6} lg={12}>
                    <StakingEvents/>
                </Grid>
                <MDBox mt={4.5}>
                    <Grid container spacing={3}>
                        <Grid item xs={12} md={6} lg={4}>
                            <MDBox mb={3}>
                                <ReportsBarChart
                                    color="info"
                                    title="website views"
                                    description="Last Campaign Performance"
                                    date="campaign sent 2 days ago"
                                    chart={reportsBarChartData}
                                />
                            </MDBox>
                        </Grid>
                        <Grid item xs={12} md={6} lg={4}>
                            <MDBox mb={3}>
                                <ReportsLineChart
                                    color="success"
                                    title="daily sales"
                                    description={
                                        <>
                                            (<strong>+15%</strong>) increase in today sales.
                                        </>
                                    }
                                    date="updated 4 min ago"
                                    chart={sales}
                                />
                            </MDBox>
                        </Grid>
                        <Grid item xs={12} md={6} lg={4}>
                            <MDBox mb={3}>
                                <ReportsLineChart
                                    color="dark"
                                    title="completed tasks"
                                    description="Last Campaign Performance"
                                    date="just updated"
                                    chart={tasks}
                                />
                            </MDBox>
                        </Grid>
                    </Grid>
                </MDBox>
                <MDBox mt={4.5}>
                    <Grid container spacing={3}>
                        <Grid item xs={12} md={6} lg={4}>
                            <MDBox mb={3}>
                                <ReportsBarChart
                                    color="info"
                                    title="website views"
                                    description="Last Campaign Performance"
                                    date="campaign sent 2 days ago"
                                    chart={reportsBarChartData}
                                />
                            </MDBox>
                        </Grid>
                        <Grid item xs={12} md={6} lg={4}>
                            <MDBox mb={3}>
                                <ReportsLineChart
                                    color="success"
                                    title="daily sales"
                                    description={
                                        <>
                                            (<strong>+15%</strong>) increase in today sales.
                                        </>
                                    }
                                    date="updated 4 min ago"
                                    chart={sales}
                                />
                            </MDBox>
                        </Grid>
                        <Grid item xs={12} md={6} lg={4}>
                            <MDBox mb={3}>
                                <ReportsLineChart
                                    color="dark"
                                    title="completed tasks"
                                    description="Last Campaign Performance"
                                    date="just updated"
                                    chart={tasks}
                                />
                            </MDBox>
                        </Grid>
                    </Grid>
                </MDBox>
            </MDBox>
            <Footer/>
        </DashboardLayout>
    );
}

export default StakingDashboard;
