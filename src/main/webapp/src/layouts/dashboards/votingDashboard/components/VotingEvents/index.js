import MDBox from "../../../../../components/MDBox";
import MDTypography from "../../../../../components/MDTypography";
import Card from "@mui/material/Card";
import React, {useEffect, useState} from "react";
import MDBadge from "../../../../../components/MDBadge";
import Grid from "@mui/material/Grid";
import axios from "axios";
import {DataGrid, GridColDef, GridRowsProp} from "@mui/x-data-grid";
import TimelineList from "../../../../../examples/Timeline/TimelineList";
import TimelineItem from "../../../../../examples/Timeline/TimelineItem";
import DefaultDoughnutChart from "../../../../../examples/Charts/DoughnutCharts/DefaultDoughnutChart";


export default function VotingEvents() {
    const [selectedItem, setSelectedItem] = useState([]);
    const [data, setData] = useState([]);
    const [pageSize, setPageSize] = React.useState(5);

    // load data from API
    useEffect(() => {
        axios("http://localhost:8080/test/voting")
            .then((res) => {
                setData(res.data);
            })
            .catch((err) => console.log(err))
    }, []);


    const rows: GridRowsProp = [];
    data.map(item => {
        rows.push({
            id: item.eventId,
            name: item.advancedName,
            type: item.payload.type === 1 ? "staking" : "voting",
            status: item.status
        })
    })

    const columns: GridColDef[] = [
        {
            field: 'icon',
            headerName: '',
            width: 50
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

    function getQuestion() {
        return <>
            {selectedItem.payload?.questions?.map(function (object, i) {
                return <MDBox>
                    <MDBox pt={1} pb={2} px={2}>
                        <MDTypography variant="h6" fontWeight="light">
                            {object.text}
                        </MDTypography>
                    </MDBox>
                    {getAnswers(object)}
                </MDBox>


            })}
        </>;
    }

    function getAnswers(object) {
        return <>
            {object.answers?.map(function (answer, i) {
                return <MDBox pt={1} pb={2} px={2} key={i}>
                    <MDTypography variant="h6" fontWeight="light">
                        <u>{answer.text}</u><br/>
                        {answer.additionalInfo}
                    </MDTypography>
                </MDBox>;
            })}
        </>;
    }


    return (
        <Grid item xs={12} md={6} lg={12}>
            <Card>
                <Grid item xs={12} md={6} lg={12}>
                    <MDBox display="flex" justifyContent="space-between" alignItems="center" p={3}>
                        <MDBox>
                            <MDTypography variant="h6" gutterBottom>
                                IOTA Voting Events
                            </MDTypography>
                        </MDBox>
                    </MDBox>
                    <MDBox px={3}>
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
                            <MDBox pt={1} pb={2} px={2}>
                                <MDTypography variant="h6" fontWeight="light">
                                    Checksum: {selectedItem.checksum} <br/>
                                    Event ID: {selectedItem.eventId}
                                </MDTypography>
                            </MDBox>
                        </Card>
                    </Grid>
                    <Grid item xs={12} md={5}>
                        <TimelineList title="Timeline">
                            <TimelineItem
                                color="success"
                                title={
                                    "Event start at " + (selectedItem.milestoneIndexStartDate ? selectedItem.milestoneIndexStartDate : "")
                                }
                                dateTime=""
                                description={
                                    "Milestone Index Start: " + (selectedItem.milestoneIndexStart ? selectedItem.milestoneIndexStart : "")
                                }
                            />
                            <TimelineItem
                                color={
                                    selectedItem.eventEndsIn === "ended" ? "error" : "secondary"
                                }
                                title={
                                    (selectedItem.eventEndsIn === "ended" ? "Event end on " : "Event will end on ") +
                                    (selectedItem.milestoneIndexEndDate ? selectedItem.milestoneIndexEndDate : "")
                                }
                                dateTime=""
                                description={
                                    "Milestone Index End: " + (selectedItem.milestoneIndexEnd ? selectedItem.milestoneIndexEnd : "")
                                }
                                lastItem
                            />
                        </TimelineList>
                    </Grid>
                </Grid>
            </MDBox>
            <MDBox mb={3} mt={4.5}>
                <Grid container spacing={3}>
                    <Grid item xs={12} md={12}>
                        <Card id="description">
                            <MDBox pt={3} px={2}>
                                <MDTypography variant="h6" fontWeight="medium">
                                    Question
                                </MDTypography>
                            </MDBox>
                            {getQuestion()}
                        </Card>
                    </Grid>
                </Grid>
            </MDBox>
            <MDBox mb={3} mt={4.5}>
                <Grid container spacing={3}>
                    <Grid item xs={12} md={6}>
                        <Card id="description">
                            <DefaultDoughnutChart
                                title="Voting Result"
                                description="current answers"
                                chart={{
                                    labels: ["Build", "Burn"],
                                    datasets: {
                                        label: "Projects",
                                        backgroundColors: ["info", "dark", "error", "secondary", "primary"],
                                        data: [372250343375, 74402847655],
                                    },
                                }}
                            />
                        </Card>
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <Card id="description">
                            <DefaultDoughnutChart
                                title="Voting Result"
                                description="current answers in percent"
                                chart={{
                                    labels: ["Build", "Burn"],
                                    datasets: {
                                        label: "Projects",
                                        backgroundColors: ["info", "dark", "error", "secondary", "primary"],
                                        data: [80, 20],
                                    },
                                }}
                            />
                        </Card>
                    </Grid>
                </Grid>
            </MDBox>
        </Grid>
    );
}

