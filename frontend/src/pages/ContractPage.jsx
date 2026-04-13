import { useEffect, useState } from "react";
import api from "../services/api";

const initialForm = {
    roomId: "",
    tenantId: "",
    startDate: "",
    endDate: "",
    depositAmount: "",
    monthlyRent: "",
    status: "ACTIVE",
    note: "",
};

function ContractPage() {
    const [contracts, setContracts] = useState([]);
    const [rooms, setRooms] = useState([]);
    const [tenants, setTenants] = useState([]);
    const [form, setForm] = useState(initialForm);
    const [error, setError] = useState("");

    const fetchContracts = async () => {
        try {
            const res = await api.get("/contracts");
            setContracts(res.data);
        } catch (err) {
            console.error(err);
            setError("Failed to load contracts");
        }
    };

    const fetchRooms = async () => {
        try {
            const res = await api.get("/rooms");
            setRooms(res.data);
        } catch (err) {
            console.error(err);
        }
    };

    const fetchTenants = async () => {
        try {
            const res = await api.get("/tenants");
            setTenants(res.data);
        } catch (err) {
            console.error(err);
        }
    };

    useEffect(() => {
        fetchContracts();
        fetchRooms();
        fetchTenants();
    }, []);

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");

        try {
            await api.post("/contracts", {
                ...form,
                roomId: Number(form.roomId),
                tenantId: Number(form.tenantId),
                depositAmount: Number(form.depositAmount),
                monthlyRent: Number(form.monthlyRent),
            });

            setForm(initialForm);
            fetchContracts();
            fetchRooms();
        } catch (err) {
            console.error(err);
            setError(err?.response?.data?.message || "Failed to create contract");
        }
    };

    const handleTerminate = async (id) => {
        try {
            await api.post(`/contracts/${id}/terminate`);
            fetchContracts();
            fetchRooms();
        } catch (err) {
            console.error(err);
            setError(err?.response?.data?.message || "Failed to terminate contract");
        }
    };

    return (
        <div className="min-h-screen bg-slate-950 text-white p-8">
            <div className="max-w-7xl mx-auto">
                <h1 className="text-3xl font-bold mb-6">Contract Management</h1>

                <div className="grid md:grid-cols-2 gap-8">
                    <div className="bg-slate-900 p-6 rounded-2xl shadow">
                        <h2 className="text-xl font-semibold mb-4">Create Contract</h2>

                        {error && (
                            <div className="bg-red-500/20 text-red-300 p-3 rounded-lg mb-4">
                                {error}
                            </div>
                        )}

                        <form onSubmit={handleSubmit} className="space-y-4">
                            <select
                                name="roomId"
                                value={form.roomId}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            >
                                <option value="">Select Room</option>
                                {rooms.map((room) => (
                                    <option key={room.id} value={room.id}>
                                        {room.roomCode} - {room.status}
                                    </option>
                                ))}
                            </select>

                            <select
                                name="tenantId"
                                value={form.tenantId}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            >
                                <option value="">Select Tenant</option>
                                {tenants.map((tenant) => (
                                    <option key={tenant.id} value={tenant.id}>
                                        {tenant.fullName}
                                    </option>
                                ))}
                            </select>

                            <input
                                type="date"
                                name="startDate"
                                value={form.startDate}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="date"
                                name="endDate"
                                value={form.endDate}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="number"
                                name="depositAmount"
                                placeholder="Deposit Amount"
                                value={form.depositAmount}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="number"
                                name="monthlyRent"
                                placeholder="Monthly Rent"
                                value={form.monthlyRent}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <select
                                name="status"
                                value={form.status}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            >
                                <option value="ACTIVE">ACTIVE</option>
                                <option value="EXPIRED">EXPIRED</option>
                                <option value="TERMINATED">TERMINATED</option>
                            </select>

                            <textarea
                                name="note"
                                placeholder="Note"
                                value={form.note}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <button
                                type="submit"
                                className="w-full bg-blue-600 hover:bg-blue-500 p-3 rounded-lg font-semibold"
                            >
                                Create Contract
                            </button>
                        </form>
                    </div>

                    <div className="bg-slate-900 p-6 rounded-2xl shadow">
                        <h2 className="text-xl font-semibold mb-4">Contract List</h2>

                        <div className="space-y-3">
                            {contracts.map((contract) => (
                                <div
                                    key={contract.id}
                                    className="border border-slate-700 rounded-xl p-4"
                                >
                                    <div className="font-semibold text-lg">
                                        Room: {contract.roomCode}
                                    </div>
                                    <div>Tenant: {contract.tenantName}</div>
                                    <div>Start Date: {contract.startDate}</div>
                                    <div>End Date: {contract.endDate}</div>
                                    <div>Deposit: {contract.depositAmount}</div>
                                    <div>Monthly Rent: {contract.monthlyRent}</div>
                                    <div>Status: {contract.status}</div>
                                    <div>Note: {contract.note || "-"}</div>

                                    {contract.status === "ACTIVE" && (
                                        <button
                                            onClick={() => handleTerminate(contract.id)}
                                            className="mt-3 bg-red-600 hover:bg-red-500 px-4 py-2 rounded-lg"
                                        >
                                            Terminate
                                        </button>
                                    )}
                                </div>
                            ))}

                            {contracts.length === 0 && (
                                <div className="text-slate-400">No contracts found</div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ContractPage;