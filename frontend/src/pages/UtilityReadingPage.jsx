import { useEffect, useState } from "react";
import api from "../services/api";

const initialForm = {
    roomId: "",
    readingMonth: "",
    electricOld: "",
    electricNew: "",
    waterOld: "",
    waterNew: "",
    electricUnitPrice: "",
    waterUnitPrice: "",
};

function UtilityReadingPage() {
    const [rooms, setRooms] = useState([]);
    const [readings, setReadings] = useState([]);
    const [form, setForm] = useState(initialForm);
    const [error, setError] = useState("");

    const fetchRooms = async () => {
        try {
            const res = await api.get("/rooms");
            setRooms(res.data);
        } catch (err) {
            console.error(err);
        }
    };

    const fetchReadings = async () => {
        try {
            const res = await api.get("/utility-readings");
            setReadings(res.data);
        } catch (err) {
            console.error(err);
            setError("Failed to load utility readings");
        }
    };

    useEffect(() => {
        fetchRooms();
        fetchReadings();
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
            await api.post("/utility-readings", {
                ...form,
                roomId: Number(form.roomId),
                electricOld: Number(form.electricOld),
                electricNew: Number(form.electricNew),
                waterOld: Number(form.waterOld),
                waterNew: Number(form.waterNew),
                electricUnitPrice: Number(form.electricUnitPrice),
                waterUnitPrice: Number(form.waterUnitPrice),
            });

            setForm(initialForm);
            fetchReadings();
        } catch (err) {
            console.error(err);
            setError(err?.response?.data?.message || "Failed to create utility reading");
        }
    };

    return (
        <div className="min-h-screen bg-slate-950 text-white p-8">
            <div className="max-w-7xl mx-auto">
                <h1 className="text-3xl font-bold mb-6">Utility Reading Management</h1>

                <div className="grid md:grid-cols-2 gap-8">
                    <div className="bg-slate-900 p-6 rounded-2xl shadow">
                        <h2 className="text-xl font-semibold mb-4">Create Utility Reading</h2>

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

                            <input
                                type="month"
                                name="readingMonth"
                                value={form.readingMonth}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="number"
                                name="electricOld"
                                placeholder="Electric Old"
                                value={form.electricOld}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="number"
                                name="electricNew"
                                placeholder="Electric New"
                                value={form.electricNew}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="number"
                                name="waterOld"
                                placeholder="Water Old"
                                value={form.waterOld}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="number"
                                name="waterNew"
                                placeholder="Water New"
                                value={form.waterNew}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="number"
                                name="electricUnitPrice"
                                placeholder="Electric Unit Price"
                                value={form.electricUnitPrice}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="number"
                                name="waterUnitPrice"
                                placeholder="Water Unit Price"
                                value={form.waterUnitPrice}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <button
                                type="submit"
                                className="w-full bg-blue-600 hover:bg-blue-500 p-3 rounded-lg font-semibold"
                            >
                                Create Utility Reading
                            </button>
                        </form>
                    </div>

                    <div className="bg-slate-900 p-6 rounded-2xl shadow">
                        <h2 className="text-xl font-semibold mb-4">Utility Reading List</h2>

                        <div className="space-y-3">
                            {readings.map((reading) => (
                                <div
                                    key={reading.id}
                                    className="border border-slate-700 rounded-xl p-4"
                                >
                                    <div className="font-semibold text-lg">
                                        Room: {reading.roomCode}
                                    </div>
                                    <div>Month: {reading.readingMonth}</div>
                                    <div>Electric: {reading.electricOld} → {reading.electricNew}</div>
                                    <div>Electric Usage: {reading.electricUsage}</div>
                                    <div>Electric Fee: {reading.electricFee}</div>
                                    <div>Water: {reading.waterOld} → {reading.waterNew}</div>
                                    <div>Water Usage: {reading.waterUsage}</div>
                                    <div>Water Fee: {reading.waterFee}</div>
                                </div>
                            ))}

                            {readings.length === 0 && (
                                <div className="text-slate-400">No utility readings found</div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default UtilityReadingPage;