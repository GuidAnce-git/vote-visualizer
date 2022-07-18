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
import Projects from "./components/Projects";
import OrdersOverview from "./components/OrdersOverview";
import LeaderboardIcon from '@mui/icons-material/Leaderboard';
import VotingEvents from "./components/VotingEvents";
import BillingInformation from "../billing/components/BillingInformation";
import Transactions from "../billing/components/Transactions";
import EventDescription from "./components/VotingEvents/EventDescription";
import EventInformation from "./components/VotingEvents/EventInformation";

function VotingDashboard() {
    const {sales, tasks} = reportsLineChartData;

    return (
        <DashboardLayout>
            <DashboardNavbar/>
            <MDBox py={3}>
                <Grid item xs={12} md={6} lg={12}>
                    <VotingEvents/>
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
                <Grid container spacing={3}>
                    <Grid item xs={12} md={6} lg={3}>
                        <MDBox mb={1.5}>
                            <ComplexStatisticsCard
                                color="dark"
                                icon={<WeekendIcon fontSize='medium'/>}
                                title="Bookings"
                                count={281}
                                percentage={{
                                    color: "success",
                                    amount: "+55%",
                                    label: "than lask week",
                                }}
                            />
                        </MDBox>
                    </Grid>
                    <Grid item xs={12} md={6} lg={3}>
                        <MDBox mb={1.5}>
                            <ComplexStatisticsCard
                                icon={<LeaderboardIcon fontSize='medium'/>}
                                title="Today's Users"
                                count="2,300"
                                percentage={{
                                    color: "success",
                                    amount: "+3%",
                                    label: "than last month",
                                }}
                            />
                        </MDBox>
                    </Grid>
                    <Grid item xs={12} md={6} lg={3}>
                        <MDBox mb={1.5}>
                            <ComplexStatisticsCard
                                color="success"
                                icon="store"
                                title="Revenue"
                                count="34k"
                                percentage={{
                                    color: "success",
                                    amount: "+1%",
                                    label: "than yesterday",
                                }}
                            />
                        </MDBox>
                    </Grid>
                    <Grid item xs={12} md={6} lg={3}>
                        <MDBox mb={1.5}>
                            <ComplexStatisticsCard
                                color="primary"
                                icon="person_add"
                                title="Followers"
                                count="+91"
                                percentage={{
                                    color: "success",
                                    amount: "",
                                    label: "Just updated",
                                }}
                            />
                        </MDBox>
                    </Grid>
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
            </MDBox>
            <Footer/>
        </DashboardLayout>
    );
}

export default VotingDashboard;
