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

import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import {MaterialUIControllerProvider} from "./context";
import {DevSupport} from "@react-buddy/ide-toolbox";
import {ComponentPreviews, useInitial} from "./dev";
import {HashRouter} from "react-router-dom";


ReactDOM.render(
    <HashRouter>
        <MaterialUIControllerProvider>
            <DevSupport ComponentPreviews={ComponentPreviews}
                        useInitialHook={useInitial}
            >
                <App/>
            </DevSupport>
        </MaterialUIControllerProvider>
    </HashRouter>,
    document.getElementById("root")
);
