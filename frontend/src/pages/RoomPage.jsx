import { useEffect, useState } from "react";
import api from "../services/api";

const initialForm = {
    roomCode: "",
    floor: 1,
    capacity: 1,
    basePrice: "",
    status: "AVAILABLE",
    note: "",
};

function RoomPage() {
    const [rooms, setRooms] = useState([]);
    const [form, setForm] = useState(initialForm);
    const [error, setError] = useState("");

    const fetchRooms = async () => {
        try {
            const res = await api.get("/rooms");
            setRooms(res.data);
        } catch (err) {
            console.error(err);
            setError("Failed to load rooms");
        }
    };

    useEffect(() => {
        fetchRooms();
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
            await api.post("/rooms", {
                ...form,
                floor: Number(form.floor),
                capacity: Number(form.capacity),
                basePrice: Number(form.basePrice),
            });

            setForm(initialForm);
            fetchRooms();
        } catch (err) {
            console.error(err);
            setError(err?.response?.data?.message || "Failed to create room");
        }
    };

    return (
        <div className="min-h-screen bg-slate-950 text-white p-8">
            <div className="max-w-6xl mx-auto">
                <h1 className="text-3xl font-bold mb-6">Room Management</h1>

                <div className="grid md:grid-cols-2 gap-8">
                    <div className="bg-slate-900 p-6 rounded-2xl shadow">
                        <h2 className="text-xl font-semibold mb-4">Create Room</h2>

                        {error && (
                            <div className="bg-red-500/20 text-red-300 p-3 rounded-lg mb-4">
                                {error}
                            </div>
                        )}

                        <form onSubmit={handleSubmit} className="space-y-4">
                            <input
                                type="text"
                                name="roomCode"
                                placeholder="Room Code"
                                value={form.roomCode}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="number"
                                name="floor"
                                placeholder="Floor"
                                value={form.floor}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="number"
                                name="capacity"
                                placeholder="Capacity"
                                value={form.capacity}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <input
                                type="number"
                                name="basePrice"
                                placeholder="Base Price"
                                value={form.basePrice}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            />

                            <select
                                name="status"
                                value={form.status}
                                onChange={handleChange}
                                className="w-full p-3 rounded-lg bg-slate-800 border border-slate-700"
                            >
                                <option value="AVAILABLE">AVAILABLE</option>
                                <option value="OCCUPIED">OCCUPIED</option>
                                <option value="MAINTENANCE">MAINTENANCE</option>
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
                                Create Room
                            </button>
                        </form>
                    </div>

                    <div className="bg-slate-900 p-6 rounded-2xl shadow">
                        <h2 className="text-xl font-semibold mb-4">Room List</h2>

                        <div className="space-y-3">
                            {rooms.map((room) => (
                                <div
                                    key={room.id}
                                    className="border border-slate-700 rounded-xl p-4"
                                >
                                    <div className="font-semibold text-lg">{room.roomCode}</div>
                                    <div>Floor: {room.floor}</div>
                                    <div>Capacity: {room.capacity}</div>
                                    <div>Base Price: {room.basePrice}</div>
                                    <div>Status: {room.status}</div>
                                    <div>Note: {room.note || "-"}</div>
                                </div>
                            ))}

                            {rooms.length === 0 && (
                                <div className="text-slate-400">No rooms found</div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default RoomPage;