import MDBox from "../../../../components/MDBox";
import MDTypography from "../../../../components/MDTypography";
import Card from "@mui/material/Card";
import React, {useEffect, useState} from "react";
import Grid from "@mui/material/Grid";
import ComplexStatisticsCard from "../../../../examples/Cards/StatisticsCards/ComplexStatisticsCard";
import HorizontalSplitIcon from '@mui/icons-material/HorizontalSplit';
import EmojiEventsIcon from '@mui/icons-material/EmojiEvents';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import axios from "axios";
import {DataGrid, GridColDef, GridRowsProp} from "@mui/x-data-grid";
import MDBadge from "../../../../components/MDBadge";
import ReportsLineChart from "../../../../examples/Charts/LineCharts/ReportsLineChart";
import reportsLineChartData from "../../data/reportsLineChartData";


export default function StakingEvents() {
    const [selectedItem, setSelectedItem] = useState([]);
    const [data, setData] = useState([]);
    const [pageSize, setPageSize] = React.useState(5);
    const {sales, tasks} = reportsLineChartData;


    // load data from API
    useEffect(() => {
        axios("http://localhost:8080/test/staking")
            .then((res) => {
                setData(res.data);
            })
            .catch((err) => console.log(err))
    }, []);


    const rows: GridRowsProp = [];
    data.map(item => {
        rows.push({
            id: item.eventId,
            icon: item.icon,
            name: item.advancedName,
            type: item.payload.type === 1 ? "staking" : "voting",
            status: item.status
        })
    })

    const columns: GridColDef[] = [
        {
            field: 'icon',
            headerName: '',
            width: 50,
            renderCell: (params) =>
                <img src={require('../../../../assets/images/small-logos/' + params.value)}
                     height={35}
                     alt=""/>,
        },
        {
            field: 'name',
            headerName: 'Name',
            flex: 1
        },
        {
            field: 'type',
            headerName: 'Type',
            flex: 0.5
        },
        {
            field: 'status',
            headerName: 'Status',
            flex: 0.5,
            renderCell: (params) =>
                <MDBox ml={-1}>
                    <MDBadge badgeContent={params.value}
                             color={params.value !== "ended" ? "success" : "dark"}
                             variant="gradient" size="sm"/>
                </MDBox>
        },
    ];


    return (<Grid item xs={12} md={6} lg={12}>
        <Card>
            <Grid item xs={12} md={6} lg={12}>
                <MDBox display="flex" justifyContent="space-between" alignItems="center" p={3}>
                    <MDBox>
                        <MDTypography variant="h6" gutterBottom>
                            IOTA Events
                        </MDTypography>
                    </MDBox>
                </MDBox>
                <MDBox>
                    <div style={{display: 'flex', height: '100%'}}>
                        <DataGrid
                            rows={rows}
                            columns={columns}
                            pageSize={pageSize}
                            onPageSizeChange={(newPageSize) => setPageSize(newPageSize)}
                            rowsPerPageOptions={[5, 10, 20]}
                            disableColumnFilter
                            hideDescendantCount
                            disableRowGrouping
                            disableColumnMenu
                            autoHeight
                            hideFooterSelectedRowCount
                            sx={{
                                boxShadow: 0,
                                border: 0,
                                '& .MuiDataGrid-row:hover': {
                                    backgroundColor: '#c7c7c7',
                                    border: 'none'
                                },
                            }}
                            onRowClick={item => setSelectedItem(data.find(item2 => item2.eventId === item.id))}
                        />
                    </div>
                </MDBox>
            </Grid>
        </Card>
        <MDBox mb={3} mt={4.5}>
            <Grid container spacing={3}>
                <Grid item xs={12} md={7}>
                    <Card id="description">
                        <MDBox pt={3} px={2}>
                            <MDTypography variant="h6" fontWeight="medium">
                                Description
                            </MDTypography>
                        </MDBox>
                        <MDBox pt={1} pb={2} px={2}>
                            <MDTypography variant="h6" fontWeight="light">
                                {selectedItem.additionalInfo}
                            </MDTypography>
                        </MDBox>
                    </Card>
                </Grid>
                <Grid item xs={12} md={5}>
                    <Card id="information">
                        <MDBox pt={3} px={2}>
                            <MDTypography variant="h6" fontWeight="medium">
                                Information
                            </MDTypography>
                        </MDBox>
                        <MDBox pt={1} pb={2} px={2}>
                            <MDTypography variant="h6" fontWeight="light">
                                <table>
                                    <tbody>
                                    <tr>
                                        <td>
                                            Created at:
                                        </td>
                                        <td>
                                            {selectedItem.createdAt}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            Last update:
                                        </td>
                                        <td>
                                            {selectedItem.updatedAt}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </MDTypography>
                        </MDBox>
                    </Card>
                </Grid>
            </Grid>
        </MDBox>
        <Grid container spacing={3}>
            <Grid item xs={12} md={6} lg={4}>
                <MDBox mb={1.5}>
                    <ComplexStatisticsCard
                        color="dark"
                        icon={<HorizontalSplitIcon fontSize='medium'/>}
                        title="staked"
                        count={selectedItem.staking?.formattedStaked}
                        percentage={{
                            color: selectedItem.staking?.percentColor,
                            amount: selectedItem.staking?.staked24hInPercent,
                            label: "in 24h",
                        }}
                    />
                </MDBox>
            </Grid>
            <Grid item xs={12} md={6} lg={4}>
                <MDBox mb={1.5}>
                    <ComplexStatisticsCard
                        icon={<EmojiEventsIcon fontSize='medium'/>}
                        title="rewarded"
                        count={selectedItem.staking?.formattedReward}
                        percentage={{
                            color: selectedItem.staking?.percentColor,
                            amount: selectedItem.staking?.rewarded24hInPercent,
                            label: "in 24h",
                        }}
                    />
                </MDBox>
            </Grid>
            <Grid item xs={12} md={6} lg={4}>
                <MDBox mb={1.5}>
                    <ComplexStatisticsCard
                        color="success"
                        icon={<AccessTimeIcon fontSize='medium'/>}
                        title="duration"
                        count={selectedItem.eventEndsIn}

                    />
                </MDBox>
            </Grid>
        </Grid>
        <MDBox mt={4.5}>
            <Grid container spacing={3}>
                <Grid item xs={12} md={8} lg={6}>
                    <MDBox mb={3}>
                        <ReportsLineChart
                            color="dark"
                            title="stake history"
                            description={
                                <>
                                    (<strong>+15%</strong>) increase in today sales.
                                </>
                            }
                            date="updated 4 min ago"
                            chart={{
                                labels: selectedItem.staking?.last12Months.split(","),
                                datasets: {
                                    label: "Mobile apps",
                                    data: []
                                }
                            }}
                        />
                    </MDBox>
                </Grid>
                <Grid item xs={12} md={8} lg={6}>
                    <MDBox mb={3}>
                        <ReportsLineChart
                            color="info"
                            title="reward history"
                            description="Last Campaign Performance"
                            date="campaign sent 2 days ago"
                            chart={{
                                labels: selectedItem.staking?.last12Months.split(","),
                                datasets: {
                                    label: selectedItem.staking?.symbol + " rewarded",
                                    data: selectedItem.staking?.rewardsLast12Months
                                }
                            }}
                        />
                    </MDBox>
                </Grid>
            </Grid>
        </MDBox>
    </Grid>)
}