import { useEffect, useState } from "react";
import api from "../services/api";

const initialForm = {
    fullName: "",
    phone: "",
    email: "",
    idNumber: "",
    birthDate: "",
    address: "",
    emergencyContact: "",
};

function TenantPage() {
    const [tenants, setTenants] = useState([]);
    const [form, setForm] = useState(initialForm);
    const [error, setError] = useState("");

    const fetchTenants = async () => {
        try {
            const res = await api.get("/tenants");
            setTenants(res.data);
        } catch (err) {
            console.error(err);
            setError("Failed to load tenants");
        }
    };

    useEffect(() => {
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
            await api.post("/tenants", form);
            setForm(initialForm);
            fetchTenants();
        } catch (err) {
            console.error(err);
            setError(err?.response?.data?.message || "Failed to create tenant");
        }
    };

    return (
        <div className="min-h-screen bg-slate-950 text-white p-8">
            <div className="max-w-6xl mx-auto">
                <h1 className="text-3xl font-bold mb-6">Tenant Management</h1>

                <div className="grid md:grid-cols-2 gap-8">
                    <div className="bg-slate-900 p-6 rounded-2xl shadow">
                        <h2 className="text-xl font-semibold mb-4">Create Tenant</h2>

                        {error && (
                            <div className="bg-red-500/20 text-red-300 p-3 rounded-lg mb-4">
                                {error}
                            </div>
                        )}

                        <form onSubmit={handleSubmit} className="space-y-4">
                            <input
                                type="text"
                                name="fullName"
                                placeholder="Full Name"
                                value={form.fullName}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="text"
                                name="phone"
                                placeholder="Phone"
                                value={form.phone}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="email"
                                name="email"
                                placeholder="Email"
                                value={form.email}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="text"
                                name="idNumber"
                                placeholder="ID Number"
                                value={form.idNumber}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="date"
                                name="birthDate"
                                value={form.birthDate}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="text"
                                name="address"
                                placeholder="Address"
                                value={form.address}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="text"
                                name="emergencyContact"
                                placeholder="Emergency Contact"
                                value={form.emergencyContact}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <button
                                type="submit"
                                className="w-full bg-blue-600 hover:bg-blue-500 p-3 rounded-lg font-semibold"
                            >
                                Create Tenant
                            </button>
                        </form>
                    </div>

                    <div className="bg-slate-900 p-6 rounded-2xl shadow">
                        <h2 className="text-xl font-semibold mb-4">Tenant List</h2>

                        <div className="space-y-3">
                            {tenants.map((tenant) => (
                                <div
                                    key={tenant.id}
                                    className="border border-slate-700 rounded-xl p-4"
                                >
                                    <div className="font-semibold text-lg">{tenant.fullName}</div>
                                    <div>Phone: {tenant.phone}</div>
                                    <div>Email: {tenant.email || "-"}</div>
                                    <div>ID Number: {tenant.idNumber}</div>
                                    <div>Birth Date: {tenant.birthDate || "-"}</div>
                                    <div>Address: {tenant.address || "-"}</div>
                                    <div>Emergency Contact: {tenant.emergencyContact || "-"}</div>
                                </div>
                            ))}

                            {tenants.length === 0 && (
                                <div className="text-slate-400">No tenants found</div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default TenantPage;