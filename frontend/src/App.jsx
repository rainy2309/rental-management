import { useState } from "react";
import RoomPage from "./pages/RoomPage";
import TenantPage from "./pages/TenantPage";
import ContractPage from "./pages/ContractPage";
import UtilityReadingPage from "./pages/UtilityReadingPage";

function App() {
    const [page, setPage] = useState("rooms");

    return (
        <div>
            <div className="bg-slate-900 text-white px-6 py-4 flex gap-4 border-b border-slate-700">
                <button
                    onClick={() => setPage("rooms")}
                    className={`px-4 py-2 rounded-lg ${page === "rooms" ? "bg-blue-600" : "bg-slate-700"}`}
                >
                    Rooms
                </button>

                <button
                    onClick={() => setPage("tenants")}
                    className={`px-4 py-2 rounded-lg ${page === "tenants" ? "bg-blue-600" : "bg-slate-700"}`}
                >
                    Tenants
                </button>

                <button
                    onClick={() => setPage("contracts")}
                    className={`px-4 py-2 rounded-lg ${page === "contracts" ? "bg-blue-600" : "bg-slate-700"}`}
                >
                    Contracts
                </button>

                <button
                    onClick={() => setPage("utilities")}
                    className={`px-4 py-2 rounded-lg ${page === "utilities" ? "bg-blue-600" : "bg-slate-700"}`}
                >
                    Utilities
                </button>
            </div>

            {page === "rooms" && <RoomPage />}
            {page === "tenants" && <TenantPage />}
            {page === "contracts" && <ContractPage />}
            {page === "utilities" && <UtilityReadingPage />}
        </div>
    );
}

export default App;